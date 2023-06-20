package me.atie.inv_itemframes;

public enum onItemFramePop {
    DESTROY, REVEAL, NOTHING;

    @Override
    public String toString(){
        String[] strings = {
                "Destroy", "Reveal", "Nothing"
        };
        return strings[this.ordinal()];
    }
}
