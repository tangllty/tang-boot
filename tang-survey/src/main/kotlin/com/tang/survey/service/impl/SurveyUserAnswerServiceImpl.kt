package com.tang.survey.service.impl

import org.springframework.stereotype.Service

import com.tang.survey.entity.SurveyUserAnswer
import com.tang.survey.mapper.SurveyUserAnswerMapper
import com.tang.survey.service.SurveyUserAnswerService

/**
 * 调查问卷用户答案业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
class SurveyUserAnswerServiceImpl(private val surveyUserAnswerMapper: SurveyUserAnswerMapper) : SurveyUserAnswerService {

    /**
     * 查询调查问卷用户答案列表
     *
     * @param surveyUserAnswer 调查问卷用户答案对象
     * @return 调查问卷用户答案列表
     */
    override fun selectSurveyUserAnswerList(surveyUserAnswer: SurveyUserAnswer): List<SurveyUserAnswer> {
        return surveyUserAnswerMapper.selectSurveyUserAnswerList(surveyUserAnswer)
    }

    /**
     * 通过调查问卷用户答案主键查询调查问卷用户答案信息
     *
     * @param answerId 调查问卷用户答案主键
     * @return 调查问卷用户答案信息
     */
    override fun selectSurveyUserAnswerByAnswerId(answerId: Long): SurveyUserAnswer {
        return surveyUserAnswerMapper.selectSurveyUserAnswerByAnswerId(answerId)
    }

    /**
     * 新增调查问卷用户答案信息
     *
     * @param surveyUserAnswer 调查问卷用户答案信息
     * @return 影响行数
     */
    override fun insertSurveyUserAnswer(surveyUserAnswer: SurveyUserAnswer): Int {
        return surveyUserAnswerMapper.insertSurveyUserAnswer(surveyUserAnswer)
    }

    /**
     * 通过调查问卷用户答案主键修改调查问卷用户答案信息
     *
     * @param surveyUserAnswer 调查问卷用户答案信息
     * @return 影响行数
     */
    override fun updateSurveyUserAnswerByAnswerId(surveyUserAnswer: SurveyUserAnswer): Int {
        return surveyUserAnswerMapper.updateSurveyUserAnswerByAnswerId(surveyUserAnswer)
    }

    /**
     * 通过调查问卷用户答案主键删除调查问卷用户答案信息
     *
     * @param answerId 调查问卷用户答案主键
     * @return 影响行数
     */
    override fun deleteSurveyUserAnswerByAnswerId(answerId: Long): Int {
        return surveyUserAnswerMapper.deleteSurveyUserAnswerByAnswerId(answerId)
    }

    /**
     * 通过调查问卷用户答案主键数组批量删除调查问卷用户答案信息
     *
     * @param answerIds 调查问卷用户答案主键数组
     * @return 影响行数
     */
    override fun deleteSurveyUserAnswerByAnswerIds(answerIds: Array<Long>): Int {
        return surveyUserAnswerMapper.deleteSurveyUserAnswerByAnswerIds(answerIds)
    }

}
