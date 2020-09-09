package com.gildedrose;

enum ItemCategory {
    AGED_BRIE, BACKSTAGE, SULFURAS_HAND_OF_RAGNAROS, ORDINARY;

    static ItemCategory getCategory(Item item) {
        switch (item.name) {
            case "Aged Brie": return AGED_BRIE;
            case "Backstage passes to a TAFKAL80ETC concert": return BACKSTAGE;
            case "Sulfuras, Hand of Ragnaros": return SULFURAS_HAND_OF_RAGNAROS;
            default: return ORDINARY;
        }
    }

}
