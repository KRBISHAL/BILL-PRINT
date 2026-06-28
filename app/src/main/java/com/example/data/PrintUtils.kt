package com.example.data

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PrintUtils {
    fun printBill(context: Context, bill: Bill) {
        val items = JsonUtils.fromJson(bill.itemsJson)
        val dateFormat = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
        val dateStr = dateFormat.format(Date(bill.createdAt))
        val isEs = bill.language.uppercase() == "ES"

        // Retrieve dynamic company details from SharedPreferences
        val prefs = context.getSharedPreferences("studio_prefs", Context.MODE_PRIVATE)
        val compName = prefs.getString("comp_name", "360 GRAPHY") ?: "360 GRAPHY"
        val compSlogan = prefs.getString("comp_slogan", "IMMERSIVE VISUALS") ?: "IMMERSIVE VISUALS"
        val compPhone = prefs.getString("comp_phone", "+1 (360) 555-0199") ?: "+1 (360) 555-0199"
        val compEmail = prefs.getString("comp_email", "hello@360graphy.com") ?: "hello@360graphy.com"
        val compAddress = prefs.getString("comp_address", "100 Creative Studio Lane, New York, NY") ?: "100 Creative Studio Lane, New York, NY"
        val compWebsite = prefs.getString("comp_website", "www.360graphy.com") ?: "www.360graphy.com"
        val compTaxId = prefs.getString("comp_tax_id", "TX-360-GRAPH") ?: "TX-360-GRAPH"
        val defaultQuote = if (isEs) "Capturando momentos hoy, creando recuerdos para toda la vida." else "Capturing moments today, creating memories for a lifetime."
        val compQuote = prefs.getString("comp_quote", defaultQuote) ?: defaultQuote

        // Calculations
        val subtotal = items.sumOf { it.quantity * it.unitPrice }
        val discount = bill.discountAmount
        val totalAfterDiscount = maxOf(0.0, subtotal - discount)
        val advance = bill.advancePayment
        val balanceDue = maxOf(0.0, totalAfterDiscount - advance)

        // Labels
        val invoiceLabel = if (isEs) "Factura" else "Invoice"
        val customerLabel = if (isEs) "Cliente" else "Customer"
        val itemsLabel = if (isEs) "Servicios / Artículos" else "Services / Items"
        val qtyLabel = if (isEs) "Cant." else "Qty"
        val priceLabel = if (isEs) "Precio" else "Price"
        val totalLabel = if (isEs) "Total a Pagar" else "Total Amount"
        val footerLabel = if (isEs) "¡Gracias por su preferencia!" else "Thank you for your business!"

        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="utf-8">
                <style>
                    /* Reset and Font styling */
                    body {
                        font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
                        color: #1C1B1F;
                        margin: 0;
                        padding: 0;
                        background: #FFFFFF;
                        -webkit-print-color-adjust: exact !important;
                        print-color-adjust: exact !important;
                    }
                    .invoice-container {
                        max-width: 800px;
                        margin: 0 auto;
                        padding: 10px;
                        page-break-inside: avoid;
                    }
                    
                    /* Header Style */
                    .header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        border-bottom: 2px solid #6750A4;
                        padding-bottom: 12px;
                        margin-bottom: 15px;
                    }
                    .logo-container {
                        display: flex;
                        align-items: center;
                        gap: 12px;
                    }
                    .logo-circle {
                        width: 44px;
                        height: 44px;
                        background-color: #6750A4;
                        color: #FFFFFF;
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-weight: bold;
                        font-size: 18px;
                    }
                    .title {
                        font-size: 20px;
                        font-weight: 600;
                        margin: 0;
                        letter-spacing: -0.5px;
                    }
                    .subtitle {
                        font-size: 12px;
                        color: #49454F;
                        margin: 2px 0 0 0;
                    }
                    
                    /* Invoice details */
                    .meta-details {
                        text-align: right;
                    }
                    .invoice-num {
                        font-size: 14px;
                        font-weight: bold;
                        color: #6750A4;
                        margin: 0;
                        text-transform: uppercase;
                    }
                    .invoice-date {
                        font-size: 11px;
                        color: #49454F;
                        margin: 3px 0 0 0;
                    }
                    
                    /* Customer Section */
                    .customer-section {
                        background-color: #FDF8F6;
                        border: 1px solid #E7E0EC;
                        border-radius: 8px;
                        padding: 10px;
                        margin-bottom: 15px;
                    }
                    .customer-title {
                        font-size: 11px;
                        font-weight: bold;
                        color: #6750A4;
                        text-transform: uppercase;
                        margin: 0 0 4px 0;
                        letter-spacing: 0.5px;
                    }
                    .customer-name {
                        font-size: 14px;
                        font-weight: 600;
                        margin: 0;
                    }
                    
                    /* Table Styles */
                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-bottom: 20px;
                    }
                    th {
                        background-color: #FDF8F6;
                        color: #1C1B1F;
                        font-size: 11px;
                        font-weight: bold;
                        text-transform: uppercase;
                        text-align: left;
                        padding: 6px 8px;
                        border-bottom: 2px solid #CAC4D0;
                    }
                    td {
                        padding: 8px;
                        font-size: 12px;
                        border-bottom: 1px solid #E7E0EC;
                        color: #1C1B1F;
                    }
                    .text-right {
                        text-align: right;
                    }
                    .text-center {
                        text-align: center;
                    }
                    
                    /* Total Section */
                    .total-container {
                        display: flex;
                        justify-content: flex-end;
                        margin-top: 15px;
                        padding-top: 10px;
                        border-top: 1px dashed #CAC4D0;
                    }
                    
                    /* Footer section */
                    .footer {
                        text-align: center;
                        margin-top: 30px;
                        font-size: 11px;
                        color: #49454F;
                        border-top: 1px solid #E7E0EC;
                        padding-top: 10px;
                    }

                    /* 3. CRITICAL PRINT RULES */
                    @media print {
                        @page {
                            size: auto;
                            margin: 5mm 10mm;
                        }
                        body {
                            background: #FFFFFF;
                            font-size: 12px;
                            -webkit-print-color-adjust: exact !important;
                            print-color-adjust: exact !important;
                        }
                        .invoice-container {
                            width: 100%;
                            padding: 0;
                            page-break-inside: avoid;
                        }
                        .header, .customer-section, table, .total-container, .footer {
                            page-break-inside: avoid;
                        }
                        td {
                            padding: 4px 8px !important; /* Compress table cell padding */
                        }
                        /* Hide app navigation or browser watermark decorations if any */
                        header, nav, .no-print {
                            display: none !important;
                        }
                    }
                </style>
            </head>
            <body>
                <div class="invoice-container">
                    <!-- Header -->
                    <div class="header">
                        <div class="logo-container">
                            <div class="logo-circle">360</div>
                            <div>
                                <h1 class="title">${compName}</h1>
                                <p class="subtitle">${compSlogan}</p>
                                <p style="font-size: 9px; color: #49454F; margin: 2px 0 0 0;">${compAddress} | Tel: ${compPhone}</p>
                                <p style="font-size: 9px; color: #49454F; margin: 1px 0 0 0;">Email: ${compEmail} | Web: ${compWebsite}</p>
                                <p style="font-size: 8px; color: #79747E; margin: 1px 0 0 0; font-weight: bold;">Tax ID: ${compTaxId}</p>
                            </div>
                        </div>
                        <div class="meta-details">
                            <p class="invoice-num">$invoiceLabel #${bill.id}</p>
                            <p class="invoice-date">$dateStr</p>
                        </div>
                    </div>

                    <!-- Customer Section -->
                    <div class="customer-section">
                        <p class="customer-title">$customerLabel</p>
                        <p class="customer-name">${bill.customerName}</p>
                    </div>

                    <!-- Table of Items -->
                    <table>
                        <thead>
                            <tr>
                                <th>$itemsLabel</th>
                                <th class="text-center" style="width: 80px;">$qtyLabel</th>
                                <th class="text-right" style="width: 120px;">$priceLabel</th>
                                <th class="text-right" style="width: 120px;">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${
                                items.joinToString("") { item ->
                                    val name = if (isEs) item.nameEs else item.nameEn
                                    val itemTotal = item.quantity * item.unitPrice
                                    """
                                    <tr>
                                        <td>${name}</td>
                                        <td class="text-center">${item.quantity}</td>
                                        <td class="text-right">$${String.format(Locale.US, "%.2f", item.unitPrice)}</td>
                                        <td class="text-right">$${String.format(Locale.US, "%.2f", itemTotal)}</td>
                                    </tr>
                                    """.trimIndent()
                                }
                            }
                        </tbody>
                    </table>

                    <!-- Total Block -->
                    <div class="total-container">
                        <table style="width: 250px; margin-left: auto; margin-bottom: 0; border: none;">
                            <tr style="border: none;">
                                <td style="border: none; padding: 2px 0; text-align: left; font-size: 11px; color: #49454F;">Subtotal:</td>
                                <td style="border: none; padding: 2px 0; text-align: right; font-size: 11px; font-weight: 500;">$${String.format(Locale.US, "%.2f", subtotal)}</td>
                            </tr>
                            ${if (discount > 0) """
                            <tr style="border: none;">
                                <td style="border: none; padding: 2px 0; text-align: left; font-size: 11px; color: #B3261E;">${if (isEs) "Descuento" else "Discount"}:</td>
                                <td style="border: none; padding: 2px 0; text-align: right; font-size: 11px; font-weight: 500; color: #B3261E;">-$${String.format(Locale.US, "%.2f", discount)}</td>
                            </tr>
                            """ else ""}
                            <tr style="border: none;">
                                <td style="border: none; padding: 2px 0; text-align: left; font-size: 11px; font-weight: bold; color: #1C1B1F;">Total:</td>
                                <td style="border: none; padding: 2px 0; text-align: right; font-size: 12px; font-weight: bold; color: #1C1B1F;">$${String.format(Locale.US, "%.2f", totalAfterDiscount)}</td>
                            </tr>
                            ${if (advance > 0) """
                            <tr style="border: none;">
                                <td style="border: none; padding: 2px 0; text-align: left; font-size: 11px; color: #49454F;">${if (isEs) "Anticipo" else "Advance"}:</td>
                                <td style="border: none; padding: 2px 0; text-align: right; font-size: 11px; font-weight: 500; color: #6750A4;">-$${String.format(Locale.US, "%.2f", advance)}</td>
                            </tr>
                            <tr style="border-top: 1px dashed #CAC4D0; padding-top: 4px;">
                                <td style="border: none; padding: 4px 0 2px 0; text-align: left; font-size: 12px; font-weight: bold; color: #6750A4;">${if (isEs) "Saldo Pendiente" else "Balance Due"}:</td>
                                <td style="border: none; padding: 4px 0 2px 0; text-align: right; font-size: 14px; font-weight: bold; color: #6750A4;">$${String.format(Locale.US, "%.2f", balanceDue)}</td>
                            </tr>
                            """ else ""}
                        </table>
                    </div>

                    <!-- Footer message -->
                    <div class="footer">
                        <p style="font-style: italic; font-size: 11px; color: #6750A4; margin-bottom: 8px; font-weight: 500;">"${compQuote}"</p>
                        <p style="margin: 0; font-size: 9px; color: #79747E;">$footerLabel</p>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()

        // Create WebView on UI thread
        val mainHandler = android.os.Handler(context.mainLooper)
        mainHandler.post {
            val webView = WebView(context)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                    val printAdapter = webView.createPrintDocumentAdapter("360Graphy_Bill_${bill.id}")
                    val jobName = "${compName} Bill #${bill.id}"
                    printManager.print(
                        jobName,
                        printAdapter,
                        PrintAttributes.Builder().build()
                    )
                }
            }
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    }
}
