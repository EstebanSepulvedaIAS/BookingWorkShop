@acceptanceTest
Feature: user component the Booking
    Background:
        * def generateUUID = function(){ return java.util.UUID.randomUUID() + '' }
        * def randomString = function() { return Math.random().toString(36).substring(2, 8).toUpperCase(); }
        * def randomEmail = function() { return 'user.' + Math.random().toString(36).substring(2, 8).toLowerCase() + '@reserves.com'}
        * def baseUrlUser = "http://localhost:8080/api/users"
        * def baseUrlLogin = "http://localhost:8080/auth/login"
        Given url baseUrlLogin
        And request { username: 'admin@reserves.com', password: 'admin' }
        When method post
        Then status 200
        * def jwtToken = response.token

    Scenario: Create User and review the password it´s correct hashed
        * def userId = generateUUID()
        * def userEmail = randomEmail()
        * def rawPassword = 'MySecretPassword123'
        * def userName = randomString()

        Given url baseUrlUser
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
            "id": "#(userId)",
            "email": "#(userEmail)",
            "password": "#(rawPassword)",
            "name": "#(userName)"
        }
        """
        When method post
        Then status 201

        Given url baseUrlUser + '/' + userId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response.password != rawPassword
        And match response ==
        """
        {
            "id": "#(userId)",
            "email": "#(userEmail)",
            "password": "#regex ^\\$2[aby]\\$\\d{2}\\$.{53}$",
            "name": "#(userName)"
        }
        """

    Scenario: Get list of all users
        Given url baseUrlUser
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 200
        And match response == '#[]'
        And match each response ==
        """
        {
            "id": "#uuid",
            "email": "#string",
            "password": "#regex ^\\$2[aby]\\$.*",
            "name": "#string"
        }
        """

    Scenario: Assign role to user
        * def userId = generateUUID()
        * def userEmail = randomEmail()

        Given url baseUrlUser
        And header Authorization = 'Bearer ' + jwtToken
        And request { id: '#(userId)', email: '#(userEmail)', password: 'Password1233', name: 'User with role'}
        When method post
        Then status 201

        Given url baseUrlUser + '/role'
        And header Authorization = 'Bearer ' + jwtToken
        And request
        """
        {
            "userId": "#(userId)",
            "roleName": "DEVELOPER"
        }
        """
        When method put
        Then status 200

    Scenario: Error with 404 not found id
        * def nonExistentId = generateUUID()
        Given url baseUrlUser + '/' + nonExistentId
        And header Authorization = 'Bearer ' + jwtToken
        When method get
        Then status 404