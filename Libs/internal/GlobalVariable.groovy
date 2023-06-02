package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p></p>
     */
    public static Object account_email
     
    /**
     * <p></p>
     */
    public static Object account_password
     
    /**
     * <p></p>
     */
    public static Object partner_id
     
    /**
     * <p></p>
     */
    public static Object app_domain
     
    /**
     * <p></p>
     */
    public static Object ar_app_name
     
    /**
     * <p></p>
     */
    public static Object excel_file_path
     
    /**
     * <p></p>
     */
    public static Object is_first_valid_inapp
     
    /**
     * <p></p>
     */
    public static Object raw_domain
     
    /**
     * <p></p>
     */
    public static Object is_first_valid_storefront
     
    /**
     * <p></p>
     */
    public static Object chrome_profile_name
     
    /**
     * <p></p>
     */
    public static Object sheet_name
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables("default")
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
            selectedVariables += TestCaseMain.getParsedValues(RunConfiguration.getOverridingParameters(), selectedVariables)
    
            account_email = selectedVariables['account_email']
            account_password = selectedVariables['account_password']
            partner_id = selectedVariables['partner_id']
            app_domain = selectedVariables['app_domain']
            ar_app_name = selectedVariables['ar_app_name']
            excel_file_path = selectedVariables['excel_file_path']
            is_first_valid_inapp = selectedVariables['is_first_valid_inapp']
            raw_domain = selectedVariables['raw_domain']
            is_first_valid_storefront = selectedVariables['is_first_valid_storefront']
            chrome_profile_name = selectedVariables['chrome_profile_name']
            sheet_name = selectedVariables['sheet_name']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
