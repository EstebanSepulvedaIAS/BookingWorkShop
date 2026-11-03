@acceptanceTest
Feature: role component the Booking
    Background:
        * def generateUUID = function(){ return java.util.UUID.randomUUID() + '' }
        * def randomString = function() { return Math.random().toString(36).substring(2, 8).toUpperCase(); }
        * def baseUrlRole = "http://localhost:8080/api/roles"
        * def baseUrlLogin = "http://localhost:8080/auth/login"

    Scenario: The admin was authenticated and create a new rol with success
        Given url baseUrlLogin
        And request { username: 'admin@reserves.com', password: 'admin' }
        When method post
        Then status 200
        * def jwtToken = response.token

        Given url baseUrlRole
        And header Authorization = 'Bearer ' + jwtToken
        And request { id: '#(generateUUID())', name: '#(randomString())'}
        When method post
        Then status 201

    Scenario: The admin gets the all roles
        Given url baseUrlLogin
        And request { username: 'admin@reserves.com', password: 'admin' }
        When method post
        Then status 200
        * def jwtToken = response.token

        Given url baseUrlRole
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response == '#[]'
        And match each response == {id: '#uuid', name: '#string' }