package com.roxx.bidmaster.domain.repository

import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.domain.model.UserResponse
import com.roxx.bidmaster.domain.model.BidId
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.SearchUser

interface BidRepository {
    /**
     * Создает нового пользователя.
     *
     * @param userRequest Объект запроса, содержащий информацию о пользователе.
     * @return Результат операции создания пользователя, содержащий объект UserResponse
     *         или ошибку, если создание не удалось.'
     */
    suspend fun createUser(userRequest: UserRequest): Result<UserResponse>

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param userRequest Объект запроса, содержащий учетные данные пользователя.
     * @return Результат операции входа, содержащий объект UserResponse
     *         или ошибку, если вход не удался.
     */
    suspend fun loginUser(userRequest: UserRequest): Result<UserResponse>

    /**
     * Получает список пользователей с наивысшими рейтингами.
     *
     * @return Результат операции, содержащий список пользователей
     *         или ошибку, если получение списка не удалось.
     */
    suspend fun getTopUsers(): Result<List<User>>

    /**
     * Получает информацию о текущем пользователе.
     *
     * @return Результат операции, содержащий объект User
     *         с информацией о текущем пользователе или ошибку.
     */
    suspend fun getMyInformation(): Result<User>

    /**
     * Делает ставку с указанной суммой.
     *
     * @param money Объект, содержащий сумму ставки.
     * @return Результат операции, содержащий объект BidResponse
     *         с информацией о ставке или ошибку, если ставка не удалась.
     */
    suspend fun makeBid(money: Money): Result<BidResponse>

    /**
     * Получает список ставок текущего пользователя.
     *
     * @return Результат операции, содержащий список ставок
     *         или ошибку, если получение списка не удалось.
     */
    suspend fun getMyBids(): Result<List<Bid>>

    /**
     * Получает идентификатор последней ставки.
     *
     * @return Результат операции, содержащий объект BidId
     *         с идентификатором последней ставки или ошибку.
     */
    suspend fun getLatBidId(): Result<BidId>

    /**
     * Удаляет ставку по указанному идентификатору.
     *
     * @param bidId Идентификатор ставки, которую нужно удалить.
     * @return Результат операции, содержащий сумму, возвращаемую после удаления ставки,
     *         или ошибку, если удаление не удалось.
     */
    suspend fun deleteBid(bidId: Int): Result<Money>

    /**
     * Проверяет действительность токена пользователя.
     *
     * @return Результат операции, содержащий строку с информацией о токене
     *         или ошибку, если токен недействителен.
     */
    suspend fun validateToken(): Result<String>

    /**
     * Ищет пользователя по заданным критериям.
     *
     * @param searchUser  Объект, содержащий параметры поиска пользователя.
     * @return Результат операции, содержащий объект User
     *         с информацией о найденном пользователе или ошибку.
     */
    suspend fun searchUser(searchUser: SearchUser): Result<User>
}