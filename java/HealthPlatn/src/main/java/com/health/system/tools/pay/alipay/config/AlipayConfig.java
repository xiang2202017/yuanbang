package com.health.system.tools.pay.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig
{
  public static String app_id = "2016081600258382";
  public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCARAi0O8Ncqh5CHClSzpqQUBsz+PrrWLc6ezVDlvmUjHQ1PwIuALDB6O2n4dY8JPRwjKCADn/7IRMnnSQoSjfcEamHmrlaIV6PxbBPK5camVmST/c/peB3LhvL7ZbcM9audnO0L8wykX76AcYOefYKsUuxPzS90uApgg4tg/RO7L3rBbWlE2A+iUZ3VQeCThnhij2zGeHwr5C2X4IO11sUP8RNLdF2jV/sdiuMa/2FDDZr02KFodfFbc+oRLFF+nf+RJB/BU1rHnwmxQ4BVpWJVTbeRsZI8TzRj5EzDwWyh7YKXWELpfAxE4wUkRdb1CqkiGLHsVccG9kxZT+tScmFAgMBAAECggEAG+myUzamPyYuS4jBXWnkrP8hzF+ki07VIP7rCnhye+dM6sF5gfVYgfpkraIx8wi/wTZ5PyToqQf7mSVTVwk/ur6FPCNprrmQUI1e3vvHeFxi57pLPiik/oqkNe7QY79MOs9AQrgcIe0TyuhT55aW+qC2ri3pFl4rthy9ZR5QLlR53kkF8w7Ed5bMdCD5u1o4dSlV0jgN8toNVrYuCimT8+Q24KlaGCguQqIs7i8NXBtiHTkZGjNBF6zZ6qBvBaZ0kpjwoduN2KlSzKDuG1iicCIljoXEZ9p1qvIPpjk0WP4sECs41P5UCge4Ms1it1D6JDLFFW9VoV8HHcrB7/FMSQKBgQDjbV9RrxY8efkNJ+OMQxivEd4/4XT9mX1d/PpVZ2GHtcV0KuYSol1upeuUyQbwRfz5UyXTAjSKMuyBuY3YxJNb/Oht3Kv4/tQ/cm8ZdZh0gtbE7sdafns8f4/ksr/ykHg4mVOQKGnNnfVSdc4/GBRbNYeFQUSmjEakk/2Gf4u4VwKBgQCQYWGH5RpoCt4yb9XAYjYsrhwEN5C2n8j5F3X2QpbZ6YzAaYA84ZKorAWNh9ZuTEsLHQAEILuKeiqK3QucaHZBST1Y5mlGRlK4F5pd3s0RLgt21344PvCJADA8PPIoBefo4gb2QVfCW3pgDyuSkijTinxx0piTZhHd+XQ4pUQTgwKBgQDXNITRJ8vz2DuafldtP4SZDWBwtGhfHSIrkRpVPGlm4NOClKF3mpqs/GaLORrOvHugdlTBckHDUGLKcopOR91lW1GZvAojqQexLhkBT2y/O5v9aDHOaQc2fULtV1QZNSn97ODM9eEAh+s/Z2iN/bwtft7JcrSCoRSr4boyznipKwKBgDfXsmVhU2lSasaPQtl6IwH9BZEL/Mjl3FGbpWBuOPJnxqzVIWhqxtK+Vhd7ppBaqdvOh6cREfhpb/LttAP202ZE8Zlk5OrtYCkb1/Teq+otKPn6NzOCBH64t+9Uk0SvpBcK9S5h75OY1ig5Nd0m8ut+8MsFbUX1gIueZDlUkpQJAoGBAKuy9gIi5MfoP0GnHiUp70ueLbELD5845ybG99f/uQpa4pRzbh/MVZaRLBIKSZR49nCKQnzs9M5RdNQponkkcLPdJjfqCLsNGFg1EdjBvQgRSf0AzWK+xJUeeXZhEFs6azege4HW1Bq7bD/1CjlZADM5xjVmEd0Jabw2KeIGRsxa";
  public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAurE8FG+vCt5cHJUqwyrBMbIoaJxQRioey+aN4VOsE3YKY1hiZyokYm9VDyhdnrMpR0ebRuc96S3Cc1LksXlG4Gfhrp0VAf/kuJiAExDzW0SrN8+ZCVd14gL/SyhBLxxPBfB8tfleK8pkdmaUMLx6UwcZ42BUC9mBkun3HY1q4XHqTjXckNh94Xo5eY4POdE+i97eAkmpGT8JlYOTxAXUYrfXndE3A8k+GJtgpU8TUAuEuXIZPQBnKnDRZB2b4vVztnEWRF0SGy1dP0OlcRFuEm2suRzr5vxD4QiukX2csf2ZJdcdHojZGlis3nZkSErCaCTfr4QXqiBkIx1/5bY0BwIDAQAB";
  public static String return_url = "http://172.20.10.3:8080/HealthPlatn/web/member/getPayInfoBack";
  public static String notify_url = "http://172.20.10.3:8080/HealthPlatn/web/member/getPayInfoAysnBack";
  public static String sign_type = "RSA2";
  public static String charset = "utf-8";
  public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
  public static String log_path = "C:\\";
  
  /* Error */
  public static void logResult(String sWord)
  {
	  FileWriter writer = null;
      try {
          writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
          writer.write(sWord);
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          if (writer != null) {
              try {
                  writer.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
  }
}