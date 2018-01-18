/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import jp.ac.asojuku.asolearning.param.AvatarKind;

/**
 * @author nishino
 *
 */
public class AvatarPartsDto {

	private AvatarDto[] avatars = new  AvatarDto[AvatarKind.MAX.getId()];

	public void setAvatarDto(AvatarKind kind,AvatarDto dto){

		avatars[kind.getId()] = dto;
	}

	public AvatarDto getAvatar(AvatarKind kind){
		return avatars[kind.getId()];
	}
}
