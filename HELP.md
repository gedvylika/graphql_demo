# Getting Started

### Build and run

To build and run tests:
> ./gradlew clean build

To start application locally:
> ./gradlew bootRun


### Test case

You can use this query to get maximum fun:
```
{
    postById(id: 1) {
        id
        title
        body
        comments {
            body
            email
            id
        }
        user {
            id
            name
            email
            address {
                street
                city
                geo {
                    lat
                    lng
                }
            }
            company {
                name
            }
        }
    }
}
```