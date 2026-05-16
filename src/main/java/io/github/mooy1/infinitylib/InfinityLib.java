package io.github.mooy1.infinitylib;

public final class InfinityLib {

    public static final String PACKAGE = InfinityLib.class.getPackage().getName();
    public static final String ADDON_PACKAGE = PACKAGE.substring(0, PACKAGE.lastIndexOf('.'));

    private InfinityLib() {}
}
