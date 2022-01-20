The Object Factory in DDD, its responsibilty is NOT only create the new objects,

but also fetch back the objects from somewhere, i.e. Database, files.

Usually, using the Repository to fetch back objects, 

but this approach does NOT delivery the business-meaning. 

Here, demonstrate the DDD approach to fetch back object in a meaningful way.

**Sample:**
```java
@Override
public void run(String... args) throws Exception {
    // Here we go ;)
    Member member = Member.BY_ID.lookup("123456789");
    if (member != null) {
        System.out.println(member.toString());
    } else {
        System.out.println("Not found the Member by id 123456789");
    }
}
```