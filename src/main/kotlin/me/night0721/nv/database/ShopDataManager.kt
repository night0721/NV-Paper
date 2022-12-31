package me.night0721.nv.database

object ShopDataManager {
    val items: HashMap<String, Int>
        get() {
            val list = HashMap<String, Int>()
            DatabaseManager().shopsDB.find().cursor().use { cursor ->
                while (cursor.hasNext()) {
                    val doc = cursor.next()
                    list[doc.getString("Name")] = doc.getInteger("Price")
                }
                return list
            }
        }
}