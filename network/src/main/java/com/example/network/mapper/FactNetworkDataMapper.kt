package com.example.network.mapper

import com.example.common.mapper.Mapper
import com.example.data.model.FactDataModel
import com.example.network.model.FactNetworkModel
import javax.inject.Inject

/**
 * Mapper class for convert [FactNetworkModel] to [FactDataModel] and vice versa
 */
class FactNetworkDataMapper @Inject constructor() : Mapper<FactNetworkModel, FactDataModel> {

    override fun from(i: FactNetworkModel): FactDataModel {
        return FactDataModel(
            fact = i.fact,
            length = i.length
        )
    }

    override fun to(o: FactDataModel): FactNetworkModel {
        return FactNetworkModel(
            fact = o.fact,
            length = o.length
        )
    }
}
