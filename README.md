<p align="center">
  <img width="400" height="200" src="lego-giphy.gif">
</p>

<h1 align="center">Builder Pattern API Testing</h1>
<h3 align="center">Automated API testing using the Builder Pattern</h3>

<div align="center">
  Simple demonstration of testing APIs using the <code>builder pattern</code> and <code>GSON</code>
</div>


<div align="center">
  <sub>Super small demo illustrating some of the stuff I've done around Test Automation :necktie:</sub>
</div>

### 

### Tools
* Project Lombok
* GSON
* Unirest

The builder patten enables you to build API requests in a simple and intuitive manner which in turn
makes test scripts easier to both read & write. Couple that with a _serialization/deserialization_ tool and you have an automated framework to test APIs.


### Sending a request
When sending an API request, the following needs to be addressed:

1. The application address
1. Any headers which the endpoint requires
1. The endpoint to send the request to
1. The data the endpoint is expecting

Say we have an application with `5` endpoints, one of which is to _create a customer_ - `/create`.
Here is an example of the `cURL` request to send to the _Create Customer_ endpoint in order to create a new customer.

##### Example request in cURL
```shell
curl -X PUT \
  http://myserver/create \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Jack",
    "surname": "Jones",
    "dob": "01/01/1980",
    "address": {
        "houseNo": 150,
        "firstLine": "Daisy Lane",
        "county": "The Shire",
        "postCode": "AB1 CD4"
    }
}`
```

Let's see how we can build the exact same request in Java.

### Build Request
We need an `object` which contains the points noted above. In this demo I have created a class called `DemoAPI` which has the following key methods:
1. `to` - the application address
2. `usingHeaders` - the headers to send
3. `createCustomer` - the _Create Customer_ endpoint which expects a `Customer` object

We create an instance of this class and being to fill in the information. 

##### Customer Object
The Customer object defines the endpoint (`/create`) and the HttpMethod (`PUT`) and provides `4` properties.
```Java
@Builder
@Getter
@Setter
@Accessors(chain = true)
public class Customer extends AbstractHttpSpec {

    private String firstName;
    private String surname;
    private String dob;
    private Address address;

    protected String getEndpoint() {
        return "/create";
    }

    protected HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }
```
and is built using the `builder()` method e.g.
```Java
Customer.builder()
        .firstName("Jack")
        .surname("Jones")
        .dob("01/01/1980")
        .address(Address.builder()
                .houseNo(150)
                .firstLine("Daisy Lane")
                .county("The Shire")
                .postCode("AB1 CD4")
                .build())
        .build();
```

Here is what the full request looks like in Java.

```Java
new DemoAPI()
                .to("http://myserver.com")
                .usingHeaders(HttpHeader.builder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build())
                .forEndpoint()
                .createCustomer(Customer.builder()
                        .firstName("Jack")
                        .surname("Jones")
                        .dob("01/01/1980")
                        .address(Address.builder()
                                .houseNo(150)
                                .firstLine("Daisy Lane")
                                .county("The Shire")
                                .postCode("AB1 CD4")
                                .build())
                        .build())
                .send();
```

### Send Request
The request is sent using `Unirest` - which is a lightweight HTTP request client Java library. We can build our `Unirest` request
using all the information we have in the `DemoAPI` object. The only sticking point is that the endpoint is expecting the request body to be a `JSON` String.

##### Java Object to JSON
Therefore we have to unmarshal the `Customer` builder object into a `JSON` String. This is done using `GSON`
```Java
String requestBody = new Gson().toJson(body);
```


### Validate Response
After sending the request, we need to validate the response. As well as the response `status code` & `status text`, let's validate the
`response body`.

The above request returns a response body which contains the created customers details - as a `JSON` String e.g.

```Java
String responseBody = "{\n" +
            "  \"firstName\": \"John\",\n" +
            "  \"surname\": \"Doe\",\n" +
            "  \"dob\": \"12/01/1981\",\n" +
            "  \"address\": {\n" +
            "    \"houseNo\": 12,\n" +
            "    \"firstLine\": \"Test Road\",\n" +
            "    \"county\": \"The Shire\",\n" +
            "    \"postCode\": \"TE5 1TG\"\n" +
            "  }\n" +
            "}";
```

##### JSON to Java Object

Using `GSON` we can marshal the `JSON` response into a Java object - in this case back to a `Customer` object.
```Java
final Customer customer = new Gson().fromJson(responseBody, Customer.class);
```

##### Assert
Once marshaled into a Java Object, we can add the necessary assertions
```Java
final Customer customer = new Gson().fromJson(responseBody, Customer.class);
Assert.assertEquals("John", customer.getFirstName());
Assert.assertEquals(12, customer.getAddress().getHouseNo());
Assert.assertEquals("TE5 1TG", customer.getAddress().getPostCode());
