package com.killkinto.zapplus.data

import com.killkinto.zapplus.data.model.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PropertyTest {
    @Test
    fun `imovel sem coordenadas nao deve ser elegivel a nenhum portal`() {
        //Dado o imóvel do portal ZAP
        val propertyZap =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.RENTAL, 4_000.0),
                address = buildAddress(geoLocation = buildGeoLocation(0.0, 0.0))
            )
        //Dado o imóvel do portal VivaReal
        val propertyVivaReal =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.RENTAL, 3_800.0),
                address = buildAddress(geoLocation = buildGeoLocation(0.0, 0.0))
            )

        //Quando verificar a legibilidade de ambos portais
        val isEligibleZap = propertyZap.isEligibleZap()
        val isEligibleVivaReal = propertyVivaReal.isEligibleVivaReal()

        //Então deve retornar, falso para ambos
        assertFalse(isEligibleZap)
        assertFalse(isEligibleVivaReal)
    }


    //Portal Zap
    @Test
    fun `quando for aluguel e o valor eh 3_500, deve retornar elegivel para o portal Zap`() {
        //Dado o imóvel para alugar
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.RENTAL, 3_500.0))

        //Quando verificar se é elegível para o portal ZAP
        val isZap = property.isEligibleZap()

        //Então, deve ser verdadeiro
        assertTrue(isZap)
    }

    @Test
    fun `quando for aluguel e o valor eh 3_501, deve retornar elegivel para o portal Zap`() {
        //Dado o imóvel para alugar
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.RENTAL, 3_501.0))

        //Quando verificar se é elegível para o portal ZAP
        val isZap = property.isEligibleZap()

        //Então, deve ser verdadeiro
        assertTrue(isZap)
    }

    @Test
    fun `quando for venda e o valor eh 600_000, deve retornar elegivel para o portal Zap`() {
        //Dado o imóvel a venda
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.SALE, 600_000.0))

        //Quando verificar se é elegível para o portal ZAP
        val isZap = property.isEligibleZap()

        //Então, deve ser verdadeiro
        assertTrue(isZap)
    }

    @Test
    fun `quando for venda e o valor eh 600_001, deve retornar elegivel para o portal Zap`() {
        //Dado o imóvel a venda
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.SALE, 600_001.0))

        //Quando verificar se é elegível para o portal ZAP
        val isZap = property.isEligibleZap()

        //Então, deve ser verdadeiro
        assertTrue(isZap)
    }

    @Test
    fun `quando for aluguel e valor eh 3_499, deve retornar inelegivel para o portal Zap`() {
        //Dado o imóvel para alugar
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.RENTAL, 3_499.0))

        //Quando verificar se é elegível para o portal ZAP
        val isZap = property.isEligibleZap()

        //Então, deve ser false
        assertFalse(isZap)
    }

    @Test
    fun `quando for venda e o valor eh 599_999, deve retornar inelegivel para o portal Zap`() {
        //Dado o imóvel para vender
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.SALE, 599_999.0))

        //Quando verificar se é elegível para o portal ZAP
        val isZap = property.isEligibleZap()

        //Então, deve ser false
        assertFalse(isZap)
    }

    @Test
    fun `Quando o valor metro quadrado for menor ou igual 3_500, o imovel a venda deve ser inelegivel para o portal Zap`() {
        //Dado o imóvel do portal ZAP
        val price = 700_000.0
        val area: Int = (price / 3500).toInt()
        val propertyZap =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.SALE, price),
                composition = buildComposicao(area = area)
            )

        val isEligibleZap = propertyZap.isEligibleZap()

        //Então, deve ser false
        assertFalse(isEligibleZap)
    }

    @Test
    fun `Quando o valor do metro quadrado for maior que 3_500, o imovel a venda deve ser elegivel para o portal Zap`() {
        //Dado o imóvel do portal ZAP
        val price = 700_500.0
        val area: Int = (price / 3500).toInt()
        val propertyZap =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.SALE, price),
                composition = buildComposicao(area = area)
            )

        val isEligibleZap = propertyZap.isEligibleZap()

        //Então, deve ser verdadeiro
        assertTrue(isEligibleZap)
    }

    @Test
    fun `Quando a area do imovel eh igual a zero, o imovel a venda deve ser inelegivel para o portal Zap`() {
        //Dado o imóvel do portal ZAP
        val propertyZap =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.SALE, 700_000.0),
                composition = buildComposicao(area = 0)
            )

        val isEligibleZap = propertyZap.isEligibleZap()

        //Então, deve ser falso
        assertFalse(isEligibleZap)
    }

    @Test
    fun `quando o imovel estiver dentro do bounding box e o seu valor para venda eh 540_000, deve retornar elegivel para o portal Zap`() {
        //Dado o imóvel do portal ZAP
        val propertyZap =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.SALE, 540_000.0),
                address = buildAddress(geoLocation = buildGeoLocation(-46.693419, -23.568704))
            )

        val isEligibleZap = propertyZap.isEligibleZap()

        //Então, deve ser verdadeiro
        assertTrue(isEligibleZap)
    }

    /**Portal Viva Real**/

    @Test
    fun `quando for aluguel e o valor eh 4_000, deve retornar elegivel para o portal Viva Real`() {
        //Dado o imóvel para alugar
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.RENTAL, 4_000.0))

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser verdadeiro
        assertTrue(isVivaReal)
    }

    @Test
    fun `quando for aluguel e o valor eh 3_999, deve retornar elegivel para o portal Viva Real`() {
        //Dado o imóvel para alugar
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.RENTAL, 3_999.0))

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser verdadeiro
        assertTrue(isVivaReal)
    }

    @Test
    fun `quando for venda e o valor eh 700_000, deve retornar elegivel para o portal Viva Real`() {
        //Dado o imóvel a vender
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.SALE, 700_000.0))

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser verdadeiro
        assertTrue(isVivaReal)
    }

    @Test
    fun `quando for venda e o valor eh 699_999, deve retornar elegivel para o portal Viva Real`() {
        //Dado o imóvel a vender
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.SALE, 699_999.0))

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser verdadeiro
        assertTrue(isVivaReal)
    }

    @Test
    fun `quando for aluguel e valor eh 4_001, deve retornar inelegivel para o portal Viva Real`() {
        //Dado o imóvel para alugar
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.RENTAL, 4_001.0))

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser false
        assertFalse(isVivaReal)
    }

    @Test
    fun `quando for venda e o valor eh 700_001, deve retornar inelegivel para o portal Viva Real`() {
        //Dado o imóvel a vender
        val property = buildProperty(infos = buildPrecingInfo(BusinessType.SALE, 700_001.0))

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser false
        assertFalse(isVivaReal)
    }

    @Test
    fun `quando o valor do condominio for maior ou igual 30 porcento do valor do aluguel, o imovel de ser inelegivel para o portal VivaReal`() {
        //Dado o imóvel para alugar
        val price = 4_000.0
        val condo = price * 0.3
        val property = buildProperty(
            infos = buildPrecingInfo(
                BusinessType.RENTAL,
                price,
                condominium = condo.toString()
            )
        )

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser false
        assertFalse(isVivaReal)
    }

    @Test
    fun `quando o valor do condominio for menor 30 porcento do valor do aluguel, o imovel de ser elegivel para o portal VivaReal`() {
        //Dado o imóvel para alugar
        val price = 4_000.0
        val condo = price * 0.3 - 1.0
        val property = buildProperty(
            infos = buildPrecingInfo(
                BusinessType.RENTAL,
                price,
                condominium = condo.toString()
            )
        )

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser verdadeiro
        assertTrue(isVivaReal)
    }

    @Test
    fun `quando o valor do condominio eh invalido, o imovel de ser inelegivel para o portal VivaReal`() {
        //Dado o imóvel para alugar
        val price = 4_000.0
        val property = buildProperty(
            infos = buildPrecingInfo(
                BusinessType.RENTAL,
                price,
                condominium = "abceei"
            )
        )

        //Quando verificar se é elegível para o portal Viva Real
        val isVivaReal = property.isEligibleVivaReal()

        //Então, deve ser falso
        assertFalse(isVivaReal)
    }

    @Test
    fun `quando o imovel estiver dentro do bounding box e o seu valor para alugar eh 6_000, deve retornar elegivel para o portal VivaReal`() {
        //Dado o imóvel do portal ZAP
        val property =
            buildProperty(
                infos = buildPrecingInfo(BusinessType.RENTAL, 6_000.0),
                address = buildAddress(geoLocation = buildGeoLocation(-46.641146, -23.546686))
            )

        val isEligibleVivaReal = property.isEligibleVivaReal()

        //Então, deve ser verdadeiro
        assertTrue(isEligibleVivaReal)
    }


    /** Builders */

    private fun buildProperty(
        composition: Composition = buildComposicao(),
        address: Address = buildAddress(),
        infos: PricingInfos = buildPrecingInfo(BusinessType.SALE, 5_000.0)
    ) = Property(
        "abc745",
        emptyList(),
        address,
        composition,
        infos
    )

    private fun buildPrecingInfo(
        businessType: BusinessType = BusinessType.RENTAL,
        price: Double = 3_800.0,
        condominium: String = "0.0"
    ) =
        PricingInfos(businessType, price, 0.0, condominium)

    private fun buildComposicao(
        area: Int = 70, parkingSpaces: Int = 1,
        bathrooms: Int = 1, bedrooms: Int = 1
    ) = Composition(area, parkingSpaces, bathrooms, bedrooms)

    private fun buildAddress(geoLocation: GeoLocation = buildGeoLocation()) =
        Address("Belo Horizonte", "Centro", geoLocation)

    private fun buildGeoLocation(log: Double = -46.716542, lat: Double = -23.502555): GeoLocation {
        return GeoLocation(log, lat)
    }
}

