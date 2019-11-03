<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:preserve-space elements="Root Customer"/>
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport"
                      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
                <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
                <title>List</title>
            </head>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="Root/Customers">
        <table>
            <thead>
                <tr>
                    <td>CustomerID</td>
                    <td>CompanyName</td>
                    <td>ContactName</td>
                    <td>Phone</td>
                    <td>FullAddress</td>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="Customer">
                    <tr>
                        <td>
                            <xsl:value-of select="@CustomerID"></xsl:value-of>
                        </td>
                        <td>
                            <xsl:value-of select="CompanyName"></xsl:value-of>
                        </td>
                        <td>
                            <xsl:value-of select="ContactName"></xsl:value-of>
                        </td>
                        <td>
                            <xsl:value-of select="Phone"></xsl:value-of>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
</xsl:stylesheet>