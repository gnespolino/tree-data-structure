# Tree Data Exam

## Description

This project is a simple REST API that allows the creation of a tree data structure.
Since the text of the exam didn't specify, I assumed that the tree would be generic, so I created a
tree that can store any type of data.
In this exam I'm gonna provide 2 implementations as payload:

1. `Maps<String, Object>`
2. `Strings`

## How to run

`./gradlew bootRun`

## Usage

### Swagger

The API is documented using Swagger. You can access the documentation at
`http://localhost:8080/api/v1/swagger-ui/index.html`

### Examples

In the following examples, I'm gonna use the `Maps<String, Object>` implementation. However just
replace the `map-tree` with `string-tree` to use the other implementation.
Also, by default, content is returned in JSON format, unless an `Accept: text/xml` header is sent (
to get the responses in XML) or `Content-Type: application/xml` (to send the payload in XML).

### Get the tree

```shell
curl -X GET http://localhost:8080/api/v1/map-tree/nodes | jq .
```

```json
{
  "nodeId": "ROOT",
  "data": {
    "name": "ROOT"
  }
}
```

### Add a node

```
POST /api/v1/map-tree/nodes/{parentId}/children?nodeId={nodeId}


{
  "key": "key",
  "value": "value"
}
```

- `nodeId` parameter is optional and allows the user to specify the id of the node to be created
- returns the id of the created node

#### Example 1 (no nodeId):

```shell
NODE_ID=$(curl -X POST http://localhost:8080/api/v1/map-tree/nodes/ROOT/children -H "Content-Type: application/json" -d '{"key": "key", "value": "value"}' 2>/dev/null)
```

```shell
echo $NODE_ID
```

```shell
curl -X GET http://localhost:8080/api/v1/map-tree/nodes | jq .
```

```json
{
  "nodeId": "ROOT",
  "data": {
    "name": "ROOT"
  },
  "children": [
    {
      "nodeId": "93ac9332-7112-4c90-ac11-8dc2fe6a9c2d",
      "data": {
        "key": "key",
        "value": "value"
      }
    }
  ]
}
```

#### Example 2 (with nodeId) -- follows from previous example:

```shell
curl -X POST "http://localhost:8080/api/v1/map-tree/nodes/$NODE_ID/children?nodeId=CHILD_1" -H "Content-Type: application/json" -d '{"key": "key", "value": "value"}' 2>/dev/null
```

```
CHILD_1
```

```shell
curl -X GET http://localhost:8080/api/v1/map-tree/nodes | jq .
```

```json
{
  "nodeId": "ROOT",
  "data": {
    "name": "ROOT"
  },
  "children": [
    {
      "nodeId": "7433e586-fc67-43c8-9916-0287e664174d",
      "data": {
        "key": "key",
        "value": "value"
      },
      "children": [
        {
          "nodeId": "CHILD_1",
          "data": {
            "key": "key",
            "value": "value"
          }
        }
      ]
    }
  ]
}
```

### Get descendants

`GET /api/v1/map-tree/nodes/{nodeId}/descendants`

```shell
curl -X GET http://localhost:8080/api/v1/map-tree/nodes/ROOT/descendants | jq .
```

```json
[
  {
    "nodeId": "7433e586-fc67-43c8-9916-0287e664174d",
    "data": {
      "key": "key",
      "value": "value"
    }
  },
  {
    "nodeId": "CHILD_1",
    "data": {
      "key": "key",
      "value": "value"
    }
  }
]
```

### Move a node

`PUT /api/v1/map-tree/nodes/{fromParentId}/children/{nodeId}?toParentIt={newParentId}`

```shell
curl -X PUT "http://localhost:8080/api/v1/map-tree/nodes/$NODE_ID/children/CHILD_1?toParentId=ROOT"
```

```json
{
  "nodeId": "ROOT",
  "data": {
    "name": "ROOT"
  },
  "children": [
    {
      "nodeId": "93ac9332-7112-4c90-ac11-8dc2fe6a9c2d",
      "data": {
        "key": "key",
        "value": "value"
      }
    },
    {
      "nodeId": "CHILD_1",
      "data": {
        "key": "key",
        "value": "value"
      }
    }
  ]
}
```

### Delete a node

`DELETE /api/v1/map-tree/nodes/{parentId}/children/{nodeId}`

```shell
curl -X DELETE "http://localhost:8080/api/v1/map-tree/nodes/ROOT/children/$NODE_ID"
```

```json
{
  "nodeId": "ROOT",
  "data": {
    "name": "ROOT"
  },
  "children": [
    {
      "nodeId": "CHILD_1",
      "data": {
        "key": "key",
        "value": "value"
      }
    }
  ]
}
```

## Tests

`./gradlew test`

## Considerations

I didn't see the need for a database storage, so I used an in-memory storage. This is due to the
fact that the tree doesn't fit well in a relational database.
If I was to use a database, I would use a NoSQL database, like MongoDB, that can store hierarchical
data, and store just 1 document, using the ID of the root node.
Of course this is good for small trees, for bigger trees, it looks like a good idea to use a
database. In that case I would have used a key-value database using
a document like this:

```json
{
  "nodeId": "<no-sql-database-key>",
  "payload": {
    ...
  },
  "children": [
    ids
    of
    children
  ]
}
```
