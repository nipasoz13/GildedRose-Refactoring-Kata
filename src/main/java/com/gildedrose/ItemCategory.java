package com.gildedrose;

enum ItemCategory {
    AGED_BRIE, BACKSTAGE, LEGENDARY, ORDINARY;

    static ItemCategory getCategory(Item item) {
        switch (item.name) {
            case "Aged Brie": return AGED_BRIE;
            case "Backstage passes to a TAFKAL80ETC concert": return BACKSTAGE;
            case "Sulfuras, Hand of Ragnaros": return LEGENDARY;
            default: return ORDINARY;
        }
    }

}
