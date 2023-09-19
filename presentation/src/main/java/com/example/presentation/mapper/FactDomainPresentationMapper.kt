package com.example.presentation.mapper

import com.example.common.mapper.Mapper
import com.example.domain.model.FactDomainModel
import com.example.presentation.model.FactPresentationModel
import javax.inject.Inject

/**
 * Mapper class for convert [FactDomainModel] to [FactPresentationModel] and vice versa
 */
class FactDomainPresentationMapper @Inject constructor() :
    Mapper<FactDomainModel, FactPresentationModel> {

    override fun from(i: FactDomainModel): FactPresentationModel {
        return FactPresentationModel(
            fact = i.fact,
            length = i.length
        )
    }

    override fun to(o: FactPresentationModel): FactDomainModel {
        return FactDomainModel(
            fact = o.fact,
            length = o.length
        )
    }
}
