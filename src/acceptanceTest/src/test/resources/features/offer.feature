@acceptanceTest
Feature: offer component the Booking
    Background:
        * def generateUUID = function(){ return java.util.UUID.randomUUID() + '' }
        * def randomTitle = function() { return 'OFFER_' + Math.random().toString(36).substring(2, 8).toUpperCase(); }
        * def baseUrlOffer = "http://localhost:8080/api/offers"
        * def baseUrlLogin = "http://localhost:8080/auth/login"
        Given url baseUrlLogin
        And request { username: 'admin@reserves.com', password: 'admin' }
        When method post
        Then status 200
        * def jwtToken = response.token
        * def defaultDate = '2026-12-15T14:00:45'
        * def defaultState = true

    Scenario: Create a new offer and find by id and title
        * def offerId = generateUUID()
        * def offerTitle = randomTitle()

        Given url baseUrlOffer
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
            "id": "#(offerId)",
            "quantity": 50,
            "availableQuantity": 50,
            "date": "#(defaultDate)",
            "state": "#(defaultState)",
            "title": "#(offerTitle)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlOffer + '/' + offerId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response ==
        """
        {
            "id": "#(offerId)",
            "quantity": 50,
            "availableQuantity": 50,
            "date": "#regex ^2026-12-15T.*",
            "state": "#(defaultState)",
            "title": "#(offerTitle)"
        }
        """

        Given url baseUrlOffer + '/title/' + offerTitle
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response.id == offerId
        And match response.title == offerTitle

    Scenario: List all offers
        Given url baseUrlOffer
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response == '#[]'
        And match each response ==
        """
        {
            "id": "#uuid",
            "quantity": "#number",
            "availableQuantity": "#number",
            "date": "#regex ^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}.*",
            "state": "#present",
            "title": "#string"
        }
        """

   Scenario: Update offer by title
        * def offerId = generateUUID()
        * def initialTitle = randomTitle()
        * def updateTitle = randomTitle()

        Given url baseUrlOffer
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
            "id": "#(offerId)",
            "quantity": 20,
            "availableQuantity": 20,
            "date": "#(defaultDate)",
            "state": "#(defaultState)",
            "title": "#(initialTitle)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlOffer + '/update/' + initialTitle
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
            "id": "#(offerId)",
            "quantity": 30,
            "availableQuantity": 15,
            "date": "#(defaultDate)",
            "state": "#(defaultState)",
            "title": "#(updateTitle)"
        }
        """
        When method put
        Then status 204

        Given url baseUrlOffer + '/' + offerId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response.quantity == 30
        And match response.availableQuantity == 15
        And match response.title == updateTitle

    Scenario: Cancel offer
        * def offerId = generateUUID()
        * def offerTitle = randomTitle()

        Given url baseUrlOffer
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
          "id": "#(offerId)",
          "quantity": 10,
          "availableQuantity": 10,
          "date": "#(defaultDate)",
          "state": "#(defaultState)",
          "title": "#(offerTitle)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlOffer + '/cancel/' + offerId
        And header Authorization = 'Bearer ' + jwtToken
        When method put
        Then status 204

    Scenario: Delete offer
        * def offerId = generateUUID()
        * def offerTitle = randomTitle()

        Given url baseUrlOffer
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
          "id": "#(offerId)",
          "quantity": 15,
          "availableQuantity": 15,
          "date": "#(defaultDate)",
          "state": "#(defaultState)",
          "title": "#(offerTitle)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlOffer + '/delete/' + offerId
        And header Authorization = 'Bearer ' + jwtToken
        When method delete
        Then status 204

        Given url baseUrlOffer + '/' + offerId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 404
