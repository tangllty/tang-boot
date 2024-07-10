package com.tang.survey.service.impl

import org.springframework.stereotype.Service

import com.tang.survey.entity.SurveyForm
import com.tang.survey.mapper.SurveyFormMapper
import com.tang.survey.service.SurveyFormService

/**
 * 调查问卷业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
class SurveyFormServiceImpl(private val surveyFormMapper: SurveyFormMapper) : SurveyFormService {

    /**
     * 查询调查问卷列表
     *
     * @param surveyForm 调查问卷对象
     * @return 调查问卷列表
     */
    override fun selectSurveyFormList(surveyForm: SurveyForm): List<SurveyForm> {
        return surveyFormMapper.selectSurveyFormList(surveyForm)
    }

    /**
     * 通过调查问卷主键查询调查问卷信息
     *
     * @param formId 调查问卷主键
     * @return 调查问卷信息
     */
    override fun selectSurveyFormByFormId(formId: Long): SurveyForm {
        return surveyFormMapper.selectSurveyFormByFormId(formId)
    }

    /**
     * 新增调查问卷信息
     *
     * @param surveyForm 调查问卷信息
     * @return 影响行数
     */
    override fun insertSurveyForm(surveyForm: SurveyForm): Int {
        return surveyFormMapper.insertSurveyForm(surveyForm)
    }

    /**
     * 通过调查问卷主键修改调查问卷信息
     *
     * @param surveyForm 调查问卷信息
     * @return 影响行数
     */
    override fun updateSurveyFormByFormId(surveyForm: SurveyForm): Int {
        return surveyFormMapper.updateSurveyFormByFormId(surveyForm)
    }

    /**
     * 通过调查问卷主键删除调查问卷信息
     *
     * @param formId 调查问卷主键
     * @return 影响行数
     */
    override fun deleteSurveyFormByFormId(formId: Long): Int {
        return surveyFormMapper.deleteSurveyFormByFormId(formId)
    }

    /**
     * 通过调查问卷主键数组批量删除调查问卷信息
     *
     * @param formIds 调查问卷主键数组
     * @return 影响行数
     */
    override fun deleteSurveyFormByFormIds(formIds: Array<Long>): Int {
        return surveyFormMapper.deleteSurveyFormByFormIds(formIds)
    }

}
