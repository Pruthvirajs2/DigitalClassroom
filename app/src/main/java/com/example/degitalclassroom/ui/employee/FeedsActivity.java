package com.example.degitalclassroom.ui.employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.degitalclassroom.R;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Feeds;
import com.example.degitalclassroom.model.Subject;
import com.example.degitalclassroom.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class FeedsActivity extends AppCompatActivity {

    //a constant to track the file chooser intent
    private static final int PICK_FILE_REQUEST = 111;

    public static final String TAG = FeedsActivity.class.getSimpleName();

    private TextInputEditText editTextContent;
    private Button postButton;
    private Spinner subjectSpinner;

    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private Uri mCropImageUri;

    private Uri mFileUriPath;
    private String fileStringPath;
    private FirebaseAuth auth;
    private DatabaseReference subjectReference, feedsReference, userReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    RadioButton fileRadioButton;
    RadioGroup fileRadioGroup;

    ImageView imageView, closeImageView;
    TextView mTextFileName;

    String checkStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        subjectReference = mFirebaseInstance.getReference("Subject");
        feedsReference = mFirebaseInstance.getReference("Feeds");
        userReference = mFirebaseInstance.getReference("users");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Feeds");

        initView();


        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        userReference.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {


                    final User currentUser = dataSnapshot.getValue(User.class);


                    subjectReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            List<String> subjects = new ArrayList<String>();

                            if (snapshot.exists()) {
                                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {

                                    String areaName = areaSnapshot.child("subName").getValue(String.class);
                                    String className = areaSnapshot.child("className").getValue(String.class);

                                    if (currentUser.getClassName().equals(className)) {
                                        subjects.add(areaName);
                                    }

                                }

                            }

                            //Creating the ArrayAdapter instance having the classrooms list
                            ArrayAdapter aa = new ArrayAdapter(FeedsActivity.this, android.R.layout.simple_spinner_item, subjects);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            subjectSpinner.setAdapter(aa);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(FeedsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String editContent = editTextContent.getText().toString().trim();
                String subjectName = subjectSpinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(editContent)) {
                    editTextContent.setError("Content is required Field.");
                    return;
                }


                if (checkStatus.equals("Gallery")) {
                    if (mCropImageUri != null) {
                        Log.d(TAG, "onClick_mCropImageUri: " + mCropImageUri.getPath());
                        uploadFile(editContent, subjectName, "0");
                    }
                } else if (checkStatus.equals("File")) {

                    if (mFileUriPath != null) {
                        Log.d(TAG, "onClick_mFileUriPath: " + mFileUriPath.getPath());
                        uploadPDFFile(editContent, subjectName, "1");
                    }
                }

             /*   if (mCropImageUri != null) {

                    Log.d(TAG, "onClick: " + mCropImageUri.getPath());
                    uploadFile(editContent, subjectName);
                }*/

            }
        });

        fileRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = fileRadioGroup.getCheckedRadioButtonId();
                fileRadioButton = (RadioButton) findViewById(selectedId);
                if (selectedId == -1) {
                    checkStatus = "";
                    Toast.makeText(FeedsActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    if (fileRadioButton.getText().equals("Gallery")) {
                        checkStatus = "Gallery";
                        CropImage.startPickImageActivity(FeedsActivity.this);
                        fileRadioGroup.clearCheck();
                    } else {
                        checkStatus = "File";
                        openFilePicker();
                        fileRadioGroup.clearCheck();

                    }

                }
            }
        });

    }


    private void initView() {

        imageView = (ImageView) findViewById(R.id.file_viewer);
        closeImageView = (ImageView) findViewById(R.id.back_image);
        postButton = (Button) findViewById(R.id.add_feed);
        editTextContent = (TextInputEditText) findViewById(R.id.input_content);
        subjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);

        fileRadioGroup = (RadioGroup) findViewById(R.id.radio_file_group);
        mTextFileName = (TextView) findViewById(R.id.file_name);
    }


    /**
     * Start pick image activity with chooser.
     */
    public void onSelectImageClick() {

    }


    public void openFilePicker() {
        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FILE_REQUEST);
    }


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            // no permissions required or already grunted, can start crop image activity
            startCropImageActivity(imageUri);
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    mTextFileName.setText("");
                    mCropImageUri = result.getUri();
                    Log.d(TAG, "mCropImageUri: " + mCropImageUri.getPath());
                    Log.d(TAG, "uploadFile: " + GetFileExtension(mCropImageUri));
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mCropImageUri);
                    Glide.with(this)
                            .load(bitmap)
                            .into(imageView);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //   Toast.makeText(this, "Cropping successful," + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

        //when the user choses the file
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                mCropImageUri = null;
                mFileUriPath = data.getData();
                mTextFileName.setText(mFileUriPath.getLastPathSegment());
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }

    }


    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    //this method will upload the file
    private void uploadFile(final String editContent, final String subjectName, final String fileType) {
        //if there is a file to upload
        if (mCropImageUri != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Log.d(TAG, "uploadFile: " + GetFileExtension(mCropImageUri));

            final StorageReference riversRef = storageReference.child("doc/" + UUID.randomUUID().toString() + ".jpg");
            riversRef.putFile(mCropImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        //getting a unique id using push().getKey() method
                        String id = feedsReference.push().getKey();


                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
                            String timestamp = sdf.format(new Date());

                            //creating an Feeds Object
                            Feeds feeds = new Feeds(id, auth.getUid(), subjectName, editContent, downloadUri.toString(), timestamp, fileType, "1");

                            feedsReference.child(id).setValue(feeds).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                    }
                                }
                            });


                        } catch (NullPointerException e) {
                            e.getStackTrace();
                        }


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    //this method will upload the file
    private void uploadPDFFile(final String editContent, final String subjectName, final String fileType) {
        //if there is a file to upload
        if (mFileUriPath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Log.d(TAG, "uploadFile: " + GetFileExtension(mFileUriPath));

            final StorageReference riversRef = storageReference.child("doc/" + UUID.randomUUID().toString() + ".pdf");
            riversRef.putFile(mFileUriPath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        //getting a unique id using push().getKey() method
                        String id = feedsReference.push().getKey();


                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
                            String timestamp = sdf.format(new Date());

                            //creating an Feeds Object
                            Feeds feeds = new Feeds(id, auth.getUid(), subjectName, editContent, downloadUri.toString(), timestamp, fileType, "1");

                            feedsReference.child(id).setValue(feeds).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                    }
                                }
                            });


                        } catch (NullPointerException e) {
                            e.getStackTrace();
                        }


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


    // Get Extension
    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Return file Extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "file.png"));
        }
        return outputFileUri;
    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", mCropImageUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        mCropImageUri = savedInstanceState.getParcelable("pic_uri");
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
