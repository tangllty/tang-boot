package com.tang.survey.mapper

import org.apache.ibatis.annotations.Mapper

import com.tang.survey.entity.SurveyUserAnswer

/**
 * 调查问卷用户答案数据访问层
 *
 * @author Tang
 */
@Mapper
interface SurveyUserAnswerMapper {

    /**
     * 查询调查问卷用户答案列表
     *
     * @param surveyUserAnswer 调查问卷用户答案对象
     * @return 调查问卷用户答案列表
     */
    fun selectSurveyUserAnswerList(surveyUserAnswer: SurveyUserAnswer): List<SurveyUserAnswer>

    /**
     * 通过调查问卷用户答案主键查询调查问卷用户答案信息
     *
     * @param answerId 调查问卷用户答案主键
     * @return 调查问卷用户答案信息
     */
    fun selectSurveyUserAnswerByAnswerId(answerId: Long): SurveyUserAnswer

    /**
     * 新增调查问卷用户答案信息
     *
     * @param surveyUserAnswer 调查问卷用户答案信息
     * @return 影响行数
     */
    fun insertSurveyUserAnswer(surveyUserAnswer: SurveyUserAnswer): Int

    /**
     * 通过调查问卷用户答案主键修改调查问卷用户答案信息
     *
     * @param surveyUserAnswer 调查问卷用户答案信息
     * @return 影响行数
     */
    fun updateSurveyUserAnswerByAnswerId(surveyUserAnswer: SurveyUserAnswer): Int

    /**
     * 通过调查问卷用户答案主键删除调查问卷用户答案信息
     *
     * @param answerId 调查问卷用户答案主键
     * @return 影响行数
     */
    fun deleteSurveyUserAnswerByAnswerId(answerId: Long): Int

    /**
     * 通过调查问卷用户答案主键数组批量删除调查问卷用户答案信息
     *
     * @param answerIds 调查问卷用户答案主键数组
     * @return 影响行数
     */
    fun deleteSurveyUserAnswerByAnswerIds(answerIds: Array<Long>): Int

}
