package com.powecn.pojo;

public class Student {

	public String studentName = "name";
	public String cog = "хож╙";
	public String level = "A";

	public Student() {

	}

	public Student(String studentName, String cog, String level) {
		this.studentName = studentName;
		this.cog = cog;
		this.level = level;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCog() {
		return cog;
	}

	public void setCog(String cog) {
		this.cog = cog;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
