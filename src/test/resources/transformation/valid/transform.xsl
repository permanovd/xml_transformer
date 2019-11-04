<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:preserve-space elements="Customer"/>
    <xsl:output method="xml" indent="yes" omit-xml-declaration="no"/>

    <xsl:template match="Customers">
        <table>
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