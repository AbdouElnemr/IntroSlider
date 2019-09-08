package com.codex.eduapp.school.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.codex.eduapp.school.R;
import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.helper.PrefManager;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.codex.eduapp.school.network.models.AddTaskModel;
import com.codex.eduapp.school.network.models.ClassessModel;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ADDHomework extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    EditText et_title, et_decription;
    TextView t_classe, t_section, te_file, t_fileseclect, t_addhome;
    MaterialSpinner spiner_subject;
    ImageView img_back;
    private String TAG = "Cancel";
    ClassessModel.Get_Classes get_classes;
    NetworkAvailable networkAvailable;
    ArrayList<MultiSelectModel> listOfClasses;
    ArrayList<MultiSelectModel> listOfselections;
    List<String> arrl_classes_ids, arrl_sections_ids;
    ArrayList<Integer> alreadySelectedclasess1 = new ArrayList<>();
    ArrayList<Integer> alreadySelectedsecetions1 = new ArrayList<>();
    List<String> list_subjects;
    List<String> list_subjects_ids;
    String id_subject;
    String tilte, decription;
    List<String> lis_claseeobjects = new ArrayList<>();
    boolean flage_subject = false, flage_class = false, flage_sections = false;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    String filePath;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhomework);
        InitsViews();
    }

    public void InitsViews() {
        et_title = findViewById(R.id.t_tilte);
        et_decription = findViewById(R.id.t_decr);
        t_addhome = findViewById(R.id.add_homework);
        t_classe = findViewById(R.id.spiner_clasee);
        t_fileseclect = findViewById(R.id.t_choosefile);
        te_file = findViewById(R.id.filed);
        t_section = findViewById(R.id.spiner_section);
        spiner_subject = findViewById(R.id.spiner_subject);
        img_back = findViewById(R.id.et_back);
        prefManager=new PrefManager(this);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        networkAvailable = new NetworkAvailable();
        if (networkAvailable.isNetworkAvailable(ADDHomework.this)) {
            AllRequestsAPI allRequestsAPI = new AllRequestsAPI(ADDHomework.this);
            allRequestsAPI.GetClasses_Method();


        } else {
            new AlertMotInternet(ADDHomework.this, et_decription);

        }
        spiner_subject.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                flage_subject = true;
                id_subject = list_subjects_ids.get(list_subjects.indexOf(item));
            }
        });


        t_classe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_subjects = new ArrayList<>();
                list_subjects_ids = new ArrayList<>();
                try {

                    MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                            .title(getResources().getString(R.string.chooseclasee)) //setting title for dialog
                            .titleSize(15)
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(1)
                            .setMaxSelectionLimit(listOfClasses.size())
                            .preSelectIDsList(alreadySelectedclasess1)
                            .multiSelectList(listOfClasses)
                            .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                                @Override
                                public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                                    //will return list of selected IDS
                                    alreadySelectedclasess1 = new ArrayList<>();
                                    arrl_classes_ids = new ArrayList<>();
                                    alreadySelectedclasess1 = selectedIds;
                                    t_classe.setText(getString(R.string.doneclass));
                                    flage_class = true;
                                    listOfselections = new ArrayList<>();
                                    for (int i = 0; i < selectedIds.size(); i++) {
                                        arrl_classes_ids.add(selectedIds.get(i) + "");
                                        int size = lis_claseeobjects.indexOf(selectedNames.get(i));
                                        Log.e("size=", size + "");


                                        for (int y = 0; y < get_classes.getData().get(size).getSections().size(); y++) {
                                            listOfselections.add(new MultiSelectModel(get_classes.getData().get(size).getSections().get(y).getSectionId(),
                                                    get_classes.getData().get(size).getSections().get(y).getSectionName()));
                                        }
                                        for (int xx = 0; xx < get_classes.getData().get(size).getSubjects().size(); xx++) {
                                            if (!list_subjects.contains(get_classes.getData().get(size).getSubjects().get(xx).getSubjectName())) {
                                                list_subjects.add(get_classes.getData().get(size).getSubjects().get(xx).getSubjectName());
                                                list_subjects_ids.add(get_classes.getData().get(size).getSubjects().get(xx).getSubjectId() + "");

                                            }
                                        }

//
                                    }

                                    spiner_subject.setItems(list_subjects);
                                    Log.e("arr", arrl_classes_ids + "");


                                }

                                @Override
                                public void onCancel() {
                                    Log.d(TAG, "Dialog cancelled");
                                }


                            });


                    multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
                } catch (Exception x) {

                }
            }

        });
        t_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                            .title(getResources().getString(R.string.pleasesection)) //setting title for dialog
                            .titleSize(15)
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(1)
                            .setMaxSelectionLimit(listOfselections.size())
                            .preSelectIDsList(alreadySelectedsecetions1)
                            .multiSelectList(listOfselections)
                            .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                                @Override
                                public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                                    //will return list of selected IDS
                                    arrl_sections_ids = new ArrayList<>();
                                    alreadySelectedsecetions1 = new ArrayList<>();
                                    alreadySelectedsecetions1 = selectedIds;
                                    for (int i = 0; i < selectedIds.size(); i++) {
                                        arrl_sections_ids.add(selectedIds.get(i) + "");


                                    }
                                    Log.e("arr", arrl_sections_ids + "");

                                    t_section.setText(getString(R.string.donesections));
                                    flage_sections = true;
                                }

                                @Override
                                public void onCancel() {
                                    Log.d(TAG, "Dialog cancelled");
                                }


                            });

                    multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

                } catch (Exception x) {

                }
            }
        });
        t_fileseclect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionsAndOpenFilePicker();
            }
        });

        t_addhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilte = et_title.getText().toString();
                decription = et_decription.getText().toString();
                String clss = "[";
                for (int t = 0; t < arrl_classes_ids.size(); t++) {

                    if (t == arrl_classes_ids.size() - 1) {
                        clss = clss + arrl_classes_ids.get(t) + "]";
                    } else {
                        clss = clss + arrl_classes_ids.get(t) + ",";
                    }

                }
                String clss1 = "[";
                for (int t = 0; t < arrl_sections_ids.size(); t++) {

                    if (t == arrl_sections_ids.size() - 1) {
                        clss1 = clss1 + arrl_sections_ids.get(t) + "]";
                    } else {
                        clss1 = clss1 + arrl_sections_ids.get(t) + ",";
                    }

                }
                Log.e("df", clss);


                if (ISValid()) {
                    if (filePath == null) {
                        Toast.makeText(ADDHomework.this, "please choose file", Toast.LENGTH_LONG).show();
                    } else {
                        if (networkAvailable.isNetworkAvailable(ADDHomework.this)) {
                            Map<String, RequestBody> map = new HashMap<>();
                            File file = new File(filePath);
                            // Parsing any Media type file


                            RequestBody token = RequestBody.create(okhttp3.MultipartBody.FORM, prefManager.getTokenreply());
                            RequestBody classes = RequestBody.create(okhttp3.MultipartBody.FORM, clss);
                            RequestBody disc = RequestBody.create(okhttp3.MultipartBody.FORM, decription);
                            RequestBody subject = RequestBody.create(okhttp3.MultipartBody.FORM, id_subject);
                            RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, tilte);
                            RequestBody sections = RequestBody.create(okhttp3.MultipartBody.FORM, clss1);

                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
                            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
                            map.put("token", token);
                            map.put("sections", sections);
                            map.put("classes", classes);
                            map.put("disc", disc);
                            map.put("subject", subject);
                            map.put("title", title);
                           new AllRequestsAPI(ADDHomework.this).ADDAssignments_methods(map);

                        } else {
                            new AlertMotInternet(ADDHomework.this, t_addhome);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withTitle("choose file")
                .start();
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
             filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            if (filePath != null) {
                File file = new File(filePath);
                te_file.setText(file.getName());
            }
        }
    }

    public boolean ISValid() {
        boolean vld = true;
        if (!flage_class) {
            vld = false;
            t_classe.setError(getString(R.string.chooseclasee));
            t_classe.requestFocus();
        }
        if (!flage_sections) {
            vld = false;
            t_section.setError(getString(R.string.pleaseselectsec));
            t_section.requestFocus();
        }
        if (!flage_subject) {
            vld = false;
            spiner_subject.setError(getString(R.string.chooseSubject));
            spiner_subject.requestFocus();
        }
        if (tilte.length() < 0) {
            vld = false;
            et_title.setError(getString(R.string.selecttitle));
            et_title.requestFocus();
        }


        return vld;
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof ClassessModel.Get_Classes ) {
            get_classes = (ClassessModel.Get_Classes) object;
            listOfClasses = new ArrayList<>();
            lis_claseeobjects = new ArrayList<>();

            for (int x = 0; x < get_classes.getData().size(); x++) {
                lis_claseeobjects.add(get_classes.getData().get(x).getClassName());
                listOfClasses.add(new MultiSelectModel(get_classes.getData().get(x).getClassId(), get_classes.getData().get(x).getClassName()));

            }
        }
        if (object instanceof AddTaskModel) {
            finish();

        }


    }

}
