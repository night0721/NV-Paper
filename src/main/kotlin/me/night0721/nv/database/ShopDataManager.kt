package me.night0721.nv.database

object ShopDataManager {
    val items: HashMap<String, Int>
        get() {
            val list = HashMap<String, Int>()
            DatabaseManager().shops.find().cursor().use { cursor ->
                if (cursor.hasNext()) {
                    val doc = cursor.next()
                    if (!doc.isNullOrEmpty())
                        list[doc.getString("Name")] = doc.getInteger("Price")
                }
                return list
            }
        }
}