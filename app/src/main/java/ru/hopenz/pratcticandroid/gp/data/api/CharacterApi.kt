package ru.hopenz.pratcticandroid.gp.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.hopenz.pratcticandroid.gp.data.model.CharacterDto

interface CharacterApi {

    @GET("{lang}/characters")
    suspend fun getCharacters(
        @Path("lang") lang: String = "en"
    ): List<CharacterDto>

}
