package jp.ac.asojuku.asolearning.param;

public enum RoleId {

	STUDENT(0,"学生"),
	TEACHER(1,"先生"),
	MANAGER(2,"管理者");

	//ステータス
	private int id;
	private String msg;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return msg1
	 */
	public String getMsg() {
		return msg;
	}


	private RoleId(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	public boolean equals(Integer id){
		if(id == null){
			return false;
		}

		return (this.id == id);
	}

	public static RoleId search(int id){
		if( STUDENT.equals(id)){
			return STUDENT;
		}else if( TEACHER.equals(id)){
			return TEACHER;
		}

		return MANAGER;
	}
}
