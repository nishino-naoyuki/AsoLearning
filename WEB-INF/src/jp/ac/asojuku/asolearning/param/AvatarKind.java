/**
 *
 */
package jp.ac.asojuku.asolearning.param;

/**
 * アバター種類
 *
レイヤの後ろから若い番号
０：後ろ髪
１：体
２：輪郭
３：耳
４：眉
５：目
６：鼻
７：口
８：前髪
９：アクセサリ１
１０：アクセサリ２
 * @author nishino
 *
 */
public enum AvatarKind {

	BACK_HAIR(0,"backhear"),//０：後ろ髪
	BODY(1,"body"),	//１：体
	FACE(2,"face"),	//２：輪郭
	EAR(3,"ear"),	//３：耳
	MAYU(4,"mayu"),	//４：眉
	EYE(5,"eye"),//５：目
	NOSE(6,"nose"),//６：鼻
	MOUTH(7,"mouth"),//７：口
	FRONT_HAIR(8,"fronthear"),//８：前髪
	ACC1(9,"acc1"),	//９：アクセサリ１
	ACC2(10,"acc2"),//１０：アクセサリ２
	MAX(11,"")
	;

	//ステータス
	private int id;
	private String dir;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return dir
	 */
	public String getDir() {
		return dir;
	}


	private AvatarKind(int id,String dir) {
		this.id = id;
		this.dir = dir;
	}

	public boolean equals(Integer id){
		if(id == null){
			return false;
		}

		return (this.id == id);
	}

	public static AvatarKind search(int id){
		if( BACK_HAIR.equals(id)){
			return BACK_HAIR;
		}else if( BODY.equals(id)){
			return BODY;
		}else if( FACE.equals(id)){
			return FACE;
		}else if( EAR.equals(id)){
			return EAR;
		}else if( MAYU.equals(id)){
			return MAYU;
		}else if( EYE.equals(id)){
			return EYE;
		}else if( NOSE.equals(id)){
			return NOSE;
		}else if( MOUTH.equals(id)){
			return MOUTH;
		}else if( FRONT_HAIR.equals(id)){
			return FRONT_HAIR;
		}else if( ACC1.equals(id)){
			return ACC1;
		}

		return ACC2;
	}

	public static AvatarKind[] getList(){

		AvatarKind[] kindList =
		{
			AvatarKind.BACK_HAIR,AvatarKind.BODY,AvatarKind.FACE,AvatarKind.EAR,
			AvatarKind.MAYU,AvatarKind.EYE,AvatarKind.NOSE,AvatarKind.MOUTH,
			AvatarKind.FRONT_HAIR,AvatarKind.ACC1,AvatarKind.ACC2
		};

		return kindList;
	}
}
