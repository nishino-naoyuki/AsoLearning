/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import jp.ac.asojuku.asolearning.entity.ResultTblEntity;

/**
 * @author nishino
 *
 */
public class ResultDao extends Dao {

	//検索SQL
	private static final String RESULT_SEARCH_SQL =
			"SELECT * FROM RESULT_TBL r "
			+ "LEFT JOIN RESULT_TESTCASE_TBL rt ON(rt.RESULT_ID = r.RESULT_ID) "
			+ "LEFT JOIN RESULT_METRICS_TBL rm ON(rm.RESULT_ID = r.RESULT_ID) "
			+ "WHERE r.USER_ID=? AND r.RESULT_ID=?";
	private static final int RESULT_SEARCH_IDX = 1;

	//挿入SQL

	//更新SQL

	private void insertOrupdateTaskResult(int userId,ResultTblEntity resultEntity){

		if( resultEntity.getResultId() == null ){
			//新規登録
		}else{
			//更新処理
		}
	}

	private ResultTblEntity getResultTblEntity(int userId,int resultid){
		ResultTblEntity resultEinty = new ResultTblEntity();


		return resultEinty;
	}
}
