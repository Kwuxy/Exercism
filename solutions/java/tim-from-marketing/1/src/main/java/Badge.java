class Badge {
    public String print(Integer id, String name, String department) {
        String idLabel = id != null ? "[" + id + "] - " : "";
        String departmentLabel = department != null ? department.toUpperCase() : "OWNER";

        return idLabel + name + " - " + departmentLabel;
    }
}
