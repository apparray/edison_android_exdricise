package com.example.data.mapper

import com.example.common.mapper.Mapper
import com.example.data.model.FactDataModel
import com.example.domain.model.FactDomainModel
import javax.inject.Inject

/**
 * Mapper class for convert [FactDataModel] to [FactDomainModel] and vice versa
 */
class FactDomainMapper @Inject constructor() : Mapper<FactDataModel, FactDomainModel> {

    override fun from(i: FactDataModel): FactDomainModel {
        return FactDomainModel(
            fact = i.fact,
            length = i.length,
        )
    }

    override fun to(o: FactDomainModel): FactDataModel {
        return FactDataModel(
            fact = o.fact,
            length = o.length
        )
    }
}
