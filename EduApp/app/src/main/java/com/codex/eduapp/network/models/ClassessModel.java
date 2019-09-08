package com.codex.eduapp.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassessModel {

    public class Datum {

        @SerializedName("class_id")
        @Expose
        private Integer classId;
        @SerializedName("class_name")
        @Expose
        private String className;
        @SerializedName("sections")
        @Expose
        private List<Section> sections = null;
        @SerializedName("subjects")
        @Expose
        private List<Subject> subjects = null;

        public Integer getClassId() {
            return classId;
        }

        public void setClassId(Integer classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }

        public List<Subject> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<Subject> subjects) {
            this.subjects = subjects;
        }

    }
    public class Subject {

        @SerializedName("subject_id")
        @Expose
        private Integer subjectId;
        @SerializedName("subject_name")
        @Expose
        private String subjectName;

        public Integer getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(Integer subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }
    }

    public class Get_Classes {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("errors")
        @Expose
        private Object errors;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public Object getErrors() {
            return errors;
        }

        public void setErrors(Object errors) {
            this.errors = errors;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }


    public class Section {

        @SerializedName("section_id")
        @Expose
        private Integer sectionId;
        @SerializedName("section_name")
        @Expose
        private String sectionName;

        public Integer getSectionId() {
            return sectionId;
        }

        public void setSectionId(Integer sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

    }
}