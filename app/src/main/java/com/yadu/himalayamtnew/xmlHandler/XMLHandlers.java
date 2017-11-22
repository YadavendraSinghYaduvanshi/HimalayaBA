package com.yadu.himalayamtnew.xmlHandler;

import com.yadu.himalayamtnew.xmlGetterSetter.AssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.Audit_QuestionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.BrandGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.CategoryMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.CompanyGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.FailureGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistreasonGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAvailabilityGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingPromotionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonComplianceChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonWorkingReasonGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.PayslipGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.SkuMasterGetterSetter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by yadavendras on 14-11-2017.
 */

public class XMLHandlers {


    // FAILURE XML HANDLER
    public static FailureGetterSetter failureXMLHandler(XmlPullParser xpp,
                                                        int eventType) {
        FailureGetterSetter failureGetterSetter = new FailureGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("STATUS")) {
                        failureGetterSetter.setStatus(xpp.nextText());
                    }
                    if (xpp.getName().equals("ERRORMSG")) {
                        failureGetterSetter.setErrorMsg(xpp.nextText());
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return failureGetterSetter;
    }


    // LOGIN XML HANDLER
    public static LoginGetterSetter loginXMLHandler(XmlPullParser xpp,
                                                    int eventType) {
        LoginGetterSetter lgs = new LoginGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("RESULT")) {
                        lgs.setResult(xpp.nextText());
                    }
                    if (xpp.getName().equals("APP_VERSION")) {
                        lgs.setVERSION(xpp.nextText());
                    }
                    if (xpp.getName().equals("APP_PATH")) {
                        lgs.setPATH(xpp.nextText());
                    }
                    if (xpp.getName().equals("CURRENTDATE")) {
                        lgs.setDATE(xpp.nextText());
                    }

                    if (xpp.getName().equals("RIGHTNAME")) {
                        lgs.setRIGHTNAME(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lgs;
    }

    // JCP XML HANDLER
    public static JourneyPlanGetterSetter JCPXMLHandler(XmlPullParser xpp, int eventType) {
        JourneyPlanGetterSetter jcpGetterSetter = new JourneyPlanGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("META_DATA")) {
                        jcpGetterSetter.setTable_journey_plan(xpp.nextText());
                    }

                    if (xpp.getName().equals("STORE_CD")) {
                        jcpGetterSetter.setStore_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("EMP_CD")) {
                        jcpGetterSetter.setEmp_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("VISIT_DATE")) {
                        jcpGetterSetter.setVISIT_DATE(xpp.nextText());
                    }
                    if (xpp.getName().equals("KEYACCOUNT")) {
                        jcpGetterSetter.setKey_account(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORENAME")) {
                        jcpGetterSetter.setStore_name(xpp.nextText());
                    }
                    if (xpp.getName().equals("CITY")) {
                        jcpGetterSetter.setCity(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORETYPE")) {
                        jcpGetterSetter.setStore_type(xpp.nextText());
                    }
                    if (xpp.getName().equals("UPLOAD_STATUS")) {
                        jcpGetterSetter.setUploadStatus(xpp.nextText());
                    }
                    /*if (xpp.getName().equals("CATEGORY_TYPE")) {
                        jcpGetterSetter.setCategory_type(xpp.nextText());
					}*/
                    if (xpp.getName().equals("CHECKOUT_STATUS")) {
                        jcpGetterSetter.setCheckOutStatus(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jcpGetterSetter;
    }



    public static SkuMasterGetterSetter storeListXML(XmlPullParser xpp,
                                                     int eventType) {
        SkuMasterGetterSetter skumaster = new SkuMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        skumaster.setSku_master_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_CD")) {
                        skumaster.setSku_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU")) {
                        skumaster.setSku(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_CD")) {
                        skumaster.setBrand_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND")) {
                        skumaster.setBrand(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY")) {
                        skumaster.setCategory(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_CD")) {
                        skumaster.setCategory_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("HIMALAYA_PHOTO")) {
                        skumaster.setHIMALAYA_PHOTO(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_PHOTO")) {
                        skumaster.setCATEGORY_PHOTO(xpp.nextText());
                    }
                    /*if (xpp.getName().equals("MRP")) {
                        skumaster.setMrp(xpp.nextText());
					}*/
				/*	if (xpp.getName().equals("CATEGORY_TYPE")) {
						skumaster.setCategory_type(xpp.nextText());
					}

					if (xpp.getName().equals("PACKING")) {
						skumaster.setPacking_size(xpp.nextText());
					}*/

                    if (xpp.getName().equals("SKU_SEQUENCE")) {
                        skumaster.setSku_sequence(xpp.nextText());
                    }

                    if (xpp.getName().equals("BRAND_SEQUENCE")) {
                        skumaster.setBrand_sequence(xpp.nextText());
                    }

                    if (xpp.getName().equals("CATEGORY_SEQUENCE")) {
                        skumaster.setCategory_sequence(xpp.nextText());
                    }


                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return skumaster;
    }


    public static PayslipGetterSetter payslipXML(XmlPullParser xpp,
                                                 int eventType) {
        PayslipGetterSetter payslip = new PayslipGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        payslip.setEmp_salary_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("SALARY_MONTH")) {
                        payslip.setMONTH(xpp.nextText());
                    }
                    if (xpp.getName().equals("SALARY_YEAR")) {
                        payslip.setSALARY_YEAR(xpp.nextText());
                    }
                    if (xpp.getName().equals("ECODE")) {
                        payslip.setECODE(xpp.nextText());
                    }
                    if (xpp.getName().equals("EMP_NAME")) {
                        payslip.setEMP_NAME(xpp.nextText());
                    }

                    if (xpp.getName().equals("PAYMENT_MODE")) {
                        payslip.setPAYMENT_MODE(xpp.nextText());
                    }

                    if (xpp.getName().equals("PRESENT_DAYS")) {
                        payslip.setPRESENT_DAYS(xpp.nextText());
                    }

                    if (xpp.getName().equals("INCENTIVE")) {
                        payslip.setINCENTIVE(xpp.nextText());
                    }

                    if (xpp.getName().equals("NATIONAL_H")) {
                        payslip.setNATIONAL_H(xpp.nextText());
                    }

                    if (xpp.getName().equals("TOTAL_EARNING")) {
                        payslip.setTOTAL_EARNING(xpp.nextText());
                    }

                    if (xpp.getName().equals("PF")) {
                        payslip.setPF(xpp.nextText());
                    }

                    if (xpp.getName().equals("ESI")) {
                        payslip.setESI(xpp.nextText());
                    }

                    if (xpp.getName().equals("PT")) {
                        payslip.setPT(xpp.nextText());
                    }

                    if (xpp.getName().equals("LWF")) {
                        payslip.setLWF(xpp.nextText());
                    }


                    if (xpp.getName().equals("MIS_DEDUCTION")) {
                        payslip.setMIS_DEDUCTION(xpp.nextText());
                    }

                    if (xpp.getName().equals("TDS")) {
                        payslip.setTDS(xpp.nextText());
                    }

                    if (xpp.getName().equals("TAKE_HOME")) {
                        payslip.setTAKE_HOME(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return payslip;
    }


    public static MappingAvailabilityGetterSetter mappingavailXML(XmlPullParser xpp,
                                                                  int eventType) {
        MappingAvailabilityGetterSetter mappingavail = new MappingAvailabilityGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        mappingavail.setMapping_avail_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_CD")) {
                        mappingavail.setStore_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("SKU_CD")) {
                        mappingavail.setSku_cd(xpp.nextText());
                    }
					/*if (xpp.getName().equals("CATEGORY_SEQUENCE")) {
						mappingavail.setCategory_sequence(xpp.nextText());
					}
					if (xpp.getName().equals("BRAND_SEQUENCE")) {
						mappingavail.setBrand_sequence(xpp.nextText());
					}
					if (xpp.getName().equals("SKU_SEQUENCE")) {
						mappingavail.setSku_sequence(xpp.nextText());
					}*/

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mappingavail;
    }


    public static MappingPromotionGetterSetter mappingpromotXML(XmlPullParser xpp,
                                                                int eventType) {
        MappingPromotionGetterSetter mappingpromo = new MappingPromotionGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        mappingpromo.setMapping_promotion_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_CD")) {
                        mappingpromo.setStore_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("PID")) {
                        mappingpromo.setPid(xpp.nextText());
                    }
                    if (xpp.getName().equals("PROMOTION")) {
                        mappingpromo.setPromotion(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_CD")) {
                        mappingpromo.setBrand_cd(xpp.nextText());
                    }
					/*if (xpp.getName().equals("SKU_SEQUENCE")) {
						mappingpromo.setSku_sequence(xpp.nextText());
					}
					if (xpp.getName().equals("CATEGORY_TYPE")) {
						mappingpromo.setCategory_type(xpp.nextText());
					}*/

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mappingpromo;
    }


    public static MappingAssetGetterSetter mappingassetXML(XmlPullParser xpp,
                                                           int eventType) {
        MappingAssetGetterSetter mappingasset = new MappingAssetGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        mappingasset.setMapping_asset_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("STORE_CD")) {
                        mappingasset.setStore_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_CD")) {
                        mappingasset.setCategory_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("ASSET_CD")) {
                        mappingasset.setAsset_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("IMAGE_URL")) {
                        mappingasset.setIMAGE_URL(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mappingasset;
    }



    public static AssetMasterGetterSetter assetMasterXML(XmlPullParser xpp,
                                                         int eventType) {
        AssetMasterGetterSetter assetmaster = new AssetMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        assetmaster.setAsset_master_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("ASSET_CD")) {
                        assetmaster.setAsset_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("ASSET")) {
                        assetmaster.setAsset(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return assetmaster;
    }


    public static CompanyGetterSetter comapnyMasterXML(XmlPullParser xpp,
                                                       int eventType) {
        CompanyGetterSetter assetmaster = new CompanyGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        assetmaster.setCompany_master_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("COMPANY_CD")) {
                        assetmaster.setCompany_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("COMPANY")) {
                        assetmaster.setCompany(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return assetmaster;
    }


    //Brand master
    public static BrandGetterSetter brandMasterXML(XmlPullParser xpp,
                                                   int eventType) {
        BrandGetterSetter brandmaster = new BrandGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        brandmaster.setBrand_master_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_CD")) {
                        brandmaster.setBrand_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND")) {
                        brandmaster.setBrand(xpp.nextText());
                    }
                    if (xpp.getName().equals("BRAND_SEQUENCE")) {
                        brandmaster.setBrand_sequence(xpp.nextText());
                    }

                    if (xpp.getName().equals("COMPANY_CD")) {
                        brandmaster.setCompany_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_CD")) {
                        brandmaster.setCategory_cd(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return brandmaster;
    }


    //Asset Checklist
    public static AssetChecklistGetterSetter assetChecklistXMLHandler(XmlPullParser xpp,
                                                                      int eventType) {
        AssetChecklistGetterSetter assetChecklistGetterSetter = new AssetChecklistGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        assetChecklistGetterSetter.setAssetchecklist_insert_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_ID")) {
                        assetChecklistGetterSetter.setCHECKLIST_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST")) {
                        assetChecklistGetterSetter.setCHECKLIST(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_TYPE")) {
                        assetChecklistGetterSetter.setTYPE(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return assetChecklistGetterSetter;
    }

    //Mapping Asset Checklist
    public static MappingAssetChecklistGetterSetter mappingAssetChecklistXMLHandler(XmlPullParser xpp,
                                                                                    int eventType) {
        MappingAssetChecklistGetterSetter mappingAssetChecklistGetterSetter = new MappingAssetChecklistGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        mappingAssetChecklistGetterSetter.setMapping_asset_checklist_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("ASSET_CD")) {
                        mappingAssetChecklistGetterSetter.setAsset_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_ID")) {
                        mappingAssetChecklistGetterSetter.setCheck_list_id(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mappingAssetChecklistGetterSetter;
    }

    public static NonWorkingReasonGetterSetter nonWorkinReasonXML(XmlPullParser xpp,
                                                                  int eventType) {
        NonWorkingReasonGetterSetter nonworking = new NonWorkingReasonGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        nonworking.setNonworking_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("REASON_CD")) {
                        nonworking.setReason_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("REASON")) {
                        nonworking.setReason(xpp.nextText());
                    }
                    if (xpp.getName().equals("ENTRY_ALLOW")) {
                        nonworking.setEntry_allow(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nonworking;
    }

    //NON_COMPLIANCE_CHECKLIST
    public static NonComplianceChecklistGetterSetter nonComplianceChecklistXML(XmlPullParser xpp,
                                                                               int eventType) {
        NonComplianceChecklistGetterSetter noncomliance = new NonComplianceChecklistGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        noncomliance.setTable_NON_COMPLIANCE_CHECKLIST(xpp.nextText());
                    }
                    if (xpp.getName().equals("CREASON_ID")) {
                        noncomliance.setCREASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CREASON")) {
                        noncomliance.setCREASON(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return noncomliance;
    }

    //MAPPING_ASSET_CHECKLIST_REASON
    public static MappingAssetChecklistreasonGetterSetter mappingAssetChecklistReasonXML(XmlPullParser xpp,
                                                                                         int eventType) {
        MappingAssetChecklistreasonGetterSetter noncomliance = new MappingAssetChecklistreasonGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        noncomliance.setTable_MAPPING_ASSET_CHECKLIST_REASON(xpp.nextText());
                    }
                    if (xpp.getName().equals("CREASON_ID")) {
                        noncomliance.setCREASON_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CHECKLIST_ID")) {
                        noncomliance.setCHECKLIST_ID(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return noncomliance;
    }


    public static CategoryMasterGetterSetter categoryMasterXML(XmlPullParser xpp,
                                                               int eventType) {
        CategoryMasterGetterSetter category = new CategoryMasterGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        category.setCategory_master_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_CD")) {
                        category.setCategory_cd(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY")) {
                        category.setCategory(xpp.nextText());
                    }

                    if (xpp.getName().equals("HIMALAYA_PHOTO")) {
                        category.setHIMALAYA_PHOTO(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY_PHOTO")) {
                        category.setCATEGORY_PHOTO(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return category;
    }

    public static Audit_QuestionGetterSetter audit_QuestionXML(XmlPullParser xpp, int eventType) {
        Audit_QuestionGetterSetter payslip = new Audit_QuestionGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {

                    if (xpp.getName().equals("META_DATA")) {
                        payslip.setAudit_question_table(xpp.nextText());
                    }
                    if (xpp.getName().equals("QUESTION_ID")) {
                        payslip.setQUESTION_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("QUESTION")) {
                        payslip.setQUESTION(xpp.nextText());
                    }
                    if (xpp.getName().equals("ANSWER_ID")) {
                        payslip.setANSWER_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("ANSWER")) {
                        payslip.setANSWER(xpp.nextText());
                    }
                    if (xpp.getName().equals("QUESTION_TYPE")) {
                        payslip.setQUESTION_TYPE(xpp.nextText());
                    }

                    if (xpp.getName().equals("CATEGORY_ID")) {
                        payslip.setCATEGORY_ID(xpp.nextText());
                    }
                    if (xpp.getName().equals("CATEGORY")) {
                        payslip.setCATEGORY(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payslip;
    }

}

