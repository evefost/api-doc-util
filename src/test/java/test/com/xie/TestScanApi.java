package test.com.xie;

import com.xie.api.ApiScanUtils;
import test.com.xie.annotation.Descript;
import test.com.xie.controller.AjaxController;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by xieyang on 17/8/25.
 */
public class TestScanApi {


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ApiScanUtils.scanPagkage("text.xie", AjaxController.class, Descript.class);
    }
}
