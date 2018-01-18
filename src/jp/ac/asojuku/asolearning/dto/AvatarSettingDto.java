/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import jp.ac.asojuku.asolearning.param.AvatarKind;

/**
 * アバター設定DTO
 * @author nishino
 *
 */
public class AvatarSettingDto {
	public static final Integer NODATA = -1;
	public static final String NOFILE = "default.png";

	private Integer[] avatars = new  Integer[AvatarKind.MAX.getId()];
	private String[] fileNames = new  String[AvatarKind.MAX.getId()];

	public AvatarSettingDto(){
		for( int i = 0 ;i < avatars.length; i++ ){
			avatars[i] = NODATA;
			fileNames[i] = NOFILE;
		}
	}

	public void setAvatarDto(AvatarKind kind,Integer id){
		avatars[kind.getId()] = id;
	}

	public void setAvatarDto(AvatarKind kind,Integer id,String fname){
		avatars[kind.getId()] = id;
		fileNames[kind.getId()] = fname;
	}

	public Integer getAvatarId(AvatarKind kind){
		return avatars[kind.getId()];
	}

	public String getAvatarName(AvatarKind kind){
		return fileNames[kind.getId()];
	}
}
