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

	private Integer[] avatars = new  Integer[AvatarKind.MAX.getId()];

	public AvatarSettingDto(){
		for( int i = 0 ;i < avatars.length; i++ ){
			avatars[i] = NODATA;
		}
	}

	public void setAvatarDto(AvatarKind kind,Integer id){
		avatars[kind.getId()] = id;
	}

	public Integer getAvatar(AvatarKind kind){
		return avatars[kind.getId()];
	}

}
