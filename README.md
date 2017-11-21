# Simple Key-Value Database

This java-based database applies hash function to implement key-value storage, including **put**, **get**, **getall**, and **delete**. There are 5 tables in it. When storing key/value, it calculates the hash integer ***d*** and ***tableIndex*** = ***d*** mod 5. In that way, when accessing data, it only read the essential table. 

The key-value support Integer, Float and String type as its value. When retrieving, it also show the data type of the value.

## Structure

* [package] **client**
    1. Controller
    2. User
* [package] **database**
    1. Table
    2. TableIndex
* [package] **utils**
    1. FileHandler
    2. KeyValueObj


## Usage

#### Compile
```
javac client/*.java database/*.java utils/*.java
```

# Quick Start
#### Execute
```
java client/Controller
```
#### Command example
```
> put k1 2
[User] put data successfully
> put k2 2.13
[User] put data successfully
> put k2 "str"
[User] put data successfully

> getall
key: k1, value: 2, type: Integer
key: k2, value: 2.13, type: Float
key: k3, value: str, type: String

> delete k1
[User] delete data successfully

> getall
key: k2, value: 2.13, type: Float
key: k3, value: str, type: String
```

# Future Work
1. Build server connection with multiple nodes handling different access
2. Implement ring with virtual nodes for scalability.