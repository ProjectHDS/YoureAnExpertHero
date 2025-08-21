package quaternary.youreanexpertharry.modules.extendedcrafting;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleExtendedCrafting extends AbstractModule {

    public static CombinationMethod COMBINATION;
    public static CompressorMethod COMPRESSOR;
    public static EnderMethod ENDER;
    public static BasicTableMethod BASIC_TABLE;
    public static AdvancedTableMethod ADVANCED_TABLE;
    public static EliteTableMethod ELITE_TABLE;
    public static UltimateTableMethod ULTIMATE_TABLE;

    public static BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();
    public static List<String> methodIds = new ArrayList<>();

    @Override
    public void init(BiMap<String, AbstractHeckMethod> heckMethods) {
        COMBINATION = registerMethod("combination", new CombinationMethod(), heckMethods);
        COMPRESSOR = registerMethod("compressor", new CompressorMethod(), heckMethods);
        ENDER = registerMethod("ender", new EnderMethod(), heckMethods);
        BASIC_TABLE = registerMethod("basic_table", new BasicTableMethod(), heckMethods);
        ADVANCED_TABLE = registerMethod("advanced_table", new AdvancedTableMethod(), heckMethods);
        ELITE_TABLE = registerMethod("elite_table", new EliteTableMethod(), heckMethods);
        ULTIMATE_TABLE = registerMethod("ultimate_table", new UltimateTableMethod(), heckMethods);
    }

    public static <T extends AbstractHeckMethod> T registerMethod(String id, T method, BiMap<String, AbstractHeckMethod> heckMethods) {
        methods.put(id, method);
        methodIds.add(id);
        heckMethods.put(id, method);
        return method;
    }

    public BiMap<String, AbstractHeckMethod> getMethods() {
        return methods;
    }

    public List<String> getMethodIds() {
        return methodIds;
    }
}
