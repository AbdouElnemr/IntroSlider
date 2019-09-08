package com.codex.eduapp.school.network.models;import com.google.gson.annotations.Expose;import com.google.gson.annotations.SerializedName;public class LoginModel {    public class Datalogin {        @SerializedName("id")        @Expose        private Integer id;        @SerializedName("name")        @Expose        private String name;        @SerializedName("user_name")        @Expose        private Object userName;        @SerializedName("email")        @Expose        private String email;        @SerializedName("mobile")        @Expose        private String mobile;        @SerializedName("address")        @Expose        private Object address;        @SerializedName("profile_pic")        @Expose        private Object profilePic;        @SerializedName("lat")        @Expose        private Object lat;        @SerializedName("lng")        @Expose        private Object lng;        @SerializedName("year")        @Expose        private Object year;        @SerializedName("isleader")        @Expose        private Integer isleader;        @SerializedName("device_token")        @Expose        private String deviceToken;        @SerializedName("role")        @Expose        private String role;        @SerializedName("status")        @Expose        private String status;        @SerializedName("school_id")        @Expose        private Object schoolId;        @SerializedName("deleted_at")        @Expose        private Object deletedAt;        @SerializedName("remember_token")        @Expose        private String rememberToken;        @SerializedName("created_at")        @Expose        private String createdAt;        @SerializedName("updated_at")        @Expose        private String updatedAt;        @SerializedName("stage_id")        @Expose        private Object stageId;        @SerializedName("parent_id")        @Expose        private Object parentId;        @SerializedName("section_id")        @Expose        private Object sectionId;        @SerializedName("class_id")        @Expose        private Object classId;        @SerializedName("level_id")        @Expose        private Object levelId;        public Integer getId() {            return id;        }        public void setId(Integer id) {            this.id = id;        }        public String getName() {            return name;        }        public void setName(String name) {            this.name = name;        }        public Object getUserName() {            return userName;        }        public void setUserName(Object userName) {            this.userName = userName;        }        public String getEmail() {            return email;        }        public void setEmail(String email) {            this.email = email;        }        public String getMobile() {            return mobile;        }        public void setMobile(String mobile) {            this.mobile = mobile;        }        public Object getAddress() {            return address;        }        public void setAddress(Object address) {            this.address = address;        }        public Object getProfilePic() {            return profilePic;        }        public void setProfilePic(Object profilePic) {            this.profilePic = profilePic;        }        public Object getLat() {            return lat;        }        public void setLat(Object lat) {            this.lat = lat;        }        public Object getLng() {            return lng;        }        public void setLng(Object lng) {            this.lng = lng;        }        public Object getYear() {            return year;        }        public void setYear(Object year) {            this.year = year;        }        public Integer getIsleader() {            return isleader;        }        public void setIsleader(Integer isleader) {            this.isleader = isleader;        }        public String getDeviceToken() {            return deviceToken;        }        public void setDeviceToken(String deviceToken) {            this.deviceToken = deviceToken;        }        public String getRole() {            return role;        }        public void setRole(String role) {            this.role = role;        }        public String getStatus() {            return status;        }        public void setStatus(String status) {            this.status = status;        }        public Object getSchoolId() {            return schoolId;        }        public void setSchoolId(Object schoolId) {            this.schoolId = schoolId;        }        public Object getDeletedAt() {            return deletedAt;        }        public void setDeletedAt(Object deletedAt) {            this.deletedAt = deletedAt;        }        public String getRememberToken() {            return rememberToken;        }        public void setRememberToken(String rememberToken) {            this.rememberToken = rememberToken;        }        public String getCreatedAt() {            return createdAt;        }        public void setCreatedAt(String createdAt) {            this.createdAt = createdAt;        }        public String getUpdatedAt() {            return updatedAt;        }        public void setUpdatedAt(String updatedAt) {            this.updatedAt = updatedAt;        }        public Object getStageId() {            return stageId;        }        public void setStageId(Object stageId) {            this.stageId = stageId;        }        public Object getParentId() {            return parentId;        }        public void setParentId(Object parentId) {            this.parentId = parentId;        }        public Object getSectionId() {            return sectionId;        }        public void setSectionId(Object sectionId) {            this.sectionId = sectionId;        }        public Object getClassId() {            return classId;        }        public void setClassId(Object classId) {            this.classId = classId;        }        public Object getLevelId() {            return levelId;        }        public void setLevelId(Object levelId) {            this.levelId = levelId;        }    }    public class LoginAPI {        @SerializedName("success")        @Expose        private String success;        @SerializedName("errors")        @Expose        private Object errors;        @SerializedName("message")        @Expose        private String message;        @SerializedName("data")        @Expose        private Datalogin data;        public String getSuccess() {            return success;        }        public void setSuccess(String success) {            this.success = success;        }        public Object getErrors() {            return errors;        }        public void setErrors(Object errors) {            this.errors = errors;        }        public String getMessage() {            return message;        }        public void setMessage(String message) {            this.message = message;        }        public Datalogin getData() {            return data;        }        public void setData(Datalogin data) {            this.data = data;        }    }}