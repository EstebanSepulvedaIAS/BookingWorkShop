@acceptanceTest
Feature: booking component the Booking
    Background:
        * def generateUUID = function(){ return java.util.UUID.randomUUID() + '' }
        * def randomEmail = function() { return 'user.' + Math.random().toString(36).substring(2, 8).toLowerCase() + '@reserves.com'}
        * def randomTitle = function() { return 'OFFER_' + Math.random().toString(36).substring(2, 8).toUpperCase(); }        
        * def baseUrlBooking = "http://localhost:8080/api/booking"
        * def baseUrlUser = "http://localhost:8080/api/users"
        * def baseUrlOffer = "http://localhost:8080/api/offers"
        * def baseUrlLogin = "http://localhost:8080/auth/login"
        Given url baseUrlLogin
        And request { username: 'admin@reserves.com', password: 'admin' }
        When method post
        Then status 200
        * def jwtToken = response.token
        * def defaultDate = '2026-12-10T14:00:45'
        * def defaultStatus = 'BOOKED'

        * def validUserId = generateUUID()
        Given url baseUrlUser
        And header Authorization = 'Bearer ' + jwtToken
        And request { id: '#(validUserId)', email: '#(randomEmail())', password: 'Password123', name: 'Booking User' }
        When method post
        Then status 201

        * def validOfferId = generateUUID()
        Given url baseUrlOffer
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
          "id": "#(validOfferId)",
          "quantity": 50,
          "availableQuantity": 50,
          "date": "#(defaultDate)",
          "state": true,
          "title": "#(randomTitle())"
        }
        """
        When method post
        Then status 201

    Scenario: Create a new booking and find by id
        * def bookingId = generateUUID()
        Given url baseUrlBooking
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
          "id": "#(bookingId)",
          "status": "#(defaultStatus)",
          "createdAt": "#(defaultDate)",
          "cancelledAt": null,
          "offerId": "#(validOfferId)",
          "userId": "#(validUserId)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlBooking + '/' + bookingId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response ==
        """
        {
          "id": "#(bookingId)",
          "status": "#(defaultStatus)",
          "createdAt": "#regex ^2026-12-10T.*",
          "cancelledAt": "##regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*",
          "offerId": "#(validOfferId)",
          "userId": "#(validUserId)"
        }
        """

    Scenario: List get all Bookings
        Given url baseUrlBooking
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response == '#[]'
        And match each response ==
        """
        {
          "id": "#uuid",
          "status": "#present",
          "createdAt": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*",
          "cancelledAt": "##regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*",
          "offerId": "#uuid",
          "userId": "#uuid"
        }
        """

    Scenario: Cancel booking by id
        * def bookingId = generateUUID()

        Given url baseUrlBooking
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
          "id": "#(bookingId)",
          "status": "#(defaultStatus)",
          "createdAt": "#(defaultDate)",
          "cancelledAt": null,
          "offerId": "#(validOfferId)",
          "userId": "#(validUserId)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlBooking + '/cancel/' + bookingId
        And header Authorization = 'Bearer ' + jwtToken
        When method put
        Then status 204

        Given url baseUrlBooking + '/' + bookingId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response.id == bookingId
        And match response.status == 'CANCELLED'
        And match response.cancelledAt == '#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*'

    Scenario: Delete booking by id
        * def bookingId = generateUUID()

        Given url baseUrlBooking
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
          "id": "#(bookingId)",
          "status": "#(defaultStatus)",
          "createdAt": "#(defaultDate)",
          "cancelledAt": null,
          "offerId": "#(validOfferId)",
          "userId": "#(validUserId)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlBooking + '/' + bookingId
        And header Authorization = 'Bearer ' + jwtToken
        When method delete
        Then status 204

        Given url baseUrlBooking + '/' + bookingId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 404