# Grow++
A Google Cloud Platform approach to finance-related RESTful services

## Short intro
This application contains some RESTful services. You can insert, list, retrieve and modify customers and transactions. The customers are persisted in Google Cloud Datastore for quick access and written to Google BigQuery for analysis purposes.
This is how the flow usually goes:
- Insert a customer
- Modify the customer using the customerId you get back from the insert response.
- Insert a transaction for this customer again using the customerId
- Get very basic classification results on a customer

## Validation on requests
- The type of a transaction is limited to a few choices:
  "pet food", "flowers", "groceries", "candy", "plutonium", "weapons", "cluster bombs"
- Fields of type date have this format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
  example: '2015-12-27T09:49:51.013Z'.
- Required fields for transactions: amount, date, id
- Required fields for customers: firstName, email, surname, country, city

## How to use the code
Deploy it or run it as a local devserver.
Go to http://<where-it-runs>/_ah/api/explorer

## Some used libraries and technologies:
- Google App Engine SDK
- Google Cloud Endpoints
- Objectify for DataStore persistence and caching.
- Bigquery
- Gson, FasterXML for (De)serialization
- Maven
- SLF4J for logging
