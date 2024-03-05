/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.edu.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import poly.edu.Model.thongKe;
import poly.edu.service.thongKeService;

/**
 *
 * @author Admin
 */
public class ThongKeForm extends javax.swing.JPanel {
    
    thongKeService sv = new thongKeService();
    int Huy = 0;
    int KH = 0;
    int TC = 0;
    float DT = 0;

    /**
     * Creates new form ThongKeForm
     */
    public ThongKeForm() {
        initComponents();
        setOpaque(false);
        byMonth();
    }
    
    public void byYear() {
        int selectedMonth = mcThang.getMonth();
        int selectedYear = ycNam.getYear();
        Huy = sv.countHuyby(selectedYear);
        KH = sv.countKH();
        TC = sv.countTCby(selectedYear);
//        double number = sv.countDTby(selectedYear);
//        DecimalFormat df = new DecimalFormat("#");
//        df.setMaximumFractionDigits(0);
//        DT = Integer.valueOf(df.format(number));
        String numberStr = String.valueOf(sv.countDTby(selectedYear));
        BigDecimal number = new BigDecimal(numberStr);
        String result = number.toPlainString();
        System.out.println(result); // Kết quả: "123450000.0"

        lblHuy.setText(String.valueOf(Huy));
        lblDT.setText(result);
        lblTC.setText(String.valueOf(TC));
        lblKH.setText(String.valueOf(KH));
        lblDT_NT.setText("Doanh thu trong năm");
        ArrayList<thongKe> tke = sv.BarChartTest(selectedYear);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (thongKe ke : tke) {
            dataset.setValue(ke.getDoanhThu(), "Số tiền", String.valueOf(ke.getThang()));
        }
        
        JFreeChart chart = ChartFactory.createBarChart("Doanh thu của Shop", "Năm" + " " + String.valueOf(selectedYear), "Số tiền",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        barchart.removeAll();
        barchart.add(barpChartPanel, BorderLayout.CENTER);
        barchart.validate();
        
    }
    
    public void byMonth() {
        int selectedMonth = mcThang.getMonth();
        Huy = sv.countHuybm(selectedMonth + 1);
        KH = sv.countKH();
        TC = sv.countTCbm(selectedMonth + 1);
//        double number = sv.countDTbm(selectedMonth + 1);
//        DecimalFormat df = new DecimalFormat("#");
//        df.setMaximumFractionDigits(0);
//        DT = Integer.valueOf(df.format(number));
//        System.out.println(df.format(number));
        String numberStr = String.valueOf(sv.countDTbm(selectedMonth + 1));
        BigDecimal number = new BigDecimal(numberStr);
        String result = number.toPlainString();
        System.out.println(result);
        
        lblHuy.setText(String.valueOf(Huy));
        lblDT.setText(result);
        lblTC.setText(String.valueOf(TC));
        lblKH.setText(String.valueOf(KH));
        lblDT_NT.setText("Doanh thu trong tháng");
        
        ArrayList<thongKe> tke = sv.BarChartTest2(selectedMonth + 1);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (thongKe ke : tke) {
//            String numberStr2 = String.valueOf(ke.getDoanhThu());
//            BigDecimal number2 = new BigDecimal(numberStr2);
//            String result2 = number2.toPlainString();
//            System.out.println(result2);
            dataset.setValue(ke.getDoanhThu(), "Số tiền", String.valueOf(ke.getThang()));
        }
        JFreeChart chart = ChartFactory.createBarChart("Doanh thu của Shop", "Tháng" + " " + String.valueOf(selectedMonth + 1), "Số tiền",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        barchart.removeAll();
        barchart.add(barpChartPanel, BorderLayout.CENTER);
        barchart.validate();
        
    }
    
    public void byTime() {
        Date selectedDate1 = dcTG1.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf1.format(selectedDate1);
        System.out.println(formattedDate);
        
        Date selectedDate2 = dcTG2.getDate();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate2 = sdf2.format(selectedDate2);
        System.out.println(formattedDate2);
        
        ArrayList<thongKe> tke = sv.BarChartTest3(formattedDate, formattedDate2);
        
        Huy = sv.countHuybt(formattedDate, formattedDate2);
        KH = sv.countKH();
        TC = sv.countTCbt(formattedDate, formattedDate2);
//        double number = sv.countDTbt(formattedDate, formattedDate2);
//        DecimalFormat df = new DecimalFormat("#");
//        df.setMaximumFractionDigits(0);
//        DT = Integer.valueOf(df.format(number));
//        System.out.println(df.format(number));

        String numberStr = String.valueOf(sv.countDTbt(formattedDate, formattedDate2));
        BigDecimal number = new BigDecimal(numberStr);
        String result = number.toPlainString();
        System.out.println(result);
        
        lblHuy.setText(String.valueOf(Huy));
        lblDT.setText(result);
        lblTC.setText(String.valueOf(TC));
        lblKH.setText(String.valueOf(KH));
        lblDT_NT.setText("Doanh thu");
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (thongKe ke : tke) {
            dataset.setValue(ke.getDoanhThu(), "Số tiền", String.valueOf(ke.getThang()));
        }
        JFreeChart chart = ChartFactory.createBarChart("Doanh thu của Shop", "Từ" + " " + String.valueOf(formattedDate) + " đến " + String.valueOf(formattedDate2), "Số tiền",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204, 0, 51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        barchart.removeAll();
        barchart.add(barpChartPanel, BorderLayout.CENTER);
        barchart.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        barchart = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        card2 = new poly.edu.Panels.Card();
        lblDT = new javax.swing.JLabel();
        lblDT_NT = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        card3 = new poly.edu.Panels.Card();
        lblHuy = new javax.swing.JLabel();
        lbTitle3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        card6 = new poly.edu.Panels.Card();
        lblKH = new javax.swing.JLabel();
        lbTitle6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        card1 = new poly.edu.Panels.Card();
        lblTC = new javax.swing.JLabel();
        lbTitle1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        mcThang = new com.toedter.calendar.JMonthChooser();
        ycNam = new com.toedter.calendar.JYearChooser();
        dcTG1 = new com.toedter.calendar.JDateChooser();
        dcTG2 = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        jScrollPane1.setViewportView(jTree1);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Bộ lọc");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/filter.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Biểu đồ thống kê");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Theo năm");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Theo tháng");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Theo khoảng thời gian");

        jLabel6.setText("Từ");

        jLabel20.setText("Đến");

        barchart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        barchart.setLayout(new java.awt.BorderLayout());

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/stats.png"))); // NOI18N

        card2.setBackground(new java.awt.Color(255, 0, 255));
        card2.setColorGradient(new java.awt.Color(55, 106, 224));

        lblDT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDT.setForeground(new java.awt.Color(255, 255, 255));
        lblDT.setText("100000000");

        lblDT_NT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDT_NT.setForeground(new java.awt.Color(255, 255, 255));
        lblDT_NT.setText("Doanh Thu");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Sales Performance.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("VND");

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card2Layout.createSequentialGroup()
                        .addComponent(lblDT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addComponent(lblDT_NT))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDT_NT)
                .addGap(18, 18, 18)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDT)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(card2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        card3.setBackground(new java.awt.Color(197, 126, 0));
        card3.setColorGradient(new java.awt.Color(255, 185, 61));

        lblHuy.setBackground(new java.awt.Color(197, 126, 0));
        lblHuy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHuy.setForeground(new java.awt.Color(255, 255, 255));
        lblHuy.setText("0");

        lbTitle3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle3.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle3.setText("Số Hóa Đơn Hủy");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Cancel.png"))); // NOI18N

        javax.swing.GroupLayout card3Layout = new javax.swing.GroupLayout(card3);
        card3.setLayout(card3Layout);
        card3Layout.setHorizontalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addGroup(card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbTitle3)
                        .addGap(10, 10, 10))
                    .addGroup(card3Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblHuy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        card3Layout.setVerticalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addGroup(card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbTitle3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHuy))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        card6.setBackground(new java.awt.Color(60, 195, 0));
        card6.setColorGradient(new java.awt.Color(208, 255, 90));

        lblKH.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblKH.setForeground(new java.awt.Color(255, 255, 255));
        lblKH.setText("50");

        lbTitle6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle6.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle6.setText("Tổng số khách hàng");

        jLabel11.setBackground(new java.awt.Color(60, 195, 0));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Client Management.png"))); // NOI18N

        javax.swing.GroupLayout card6Layout = new javax.swing.GroupLayout(card6);
        card6.setLayout(card6Layout);
        card6Layout.setHorizontalGroup(
            card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTitle6)
                .addGap(111, 111, 111))
        );
        card6Layout.setVerticalGroup(
            card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6Layout.createSequentialGroup()
                .addComponent(lbTitle6)
                .addGroup(card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card6Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblKH))
                    .addGroup(card6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        card1.setBackground(new java.awt.Color(72, 111, 252));
        card1.setColorGradient(new java.awt.Color(10, 30, 214));

        lblTC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTC.setForeground(new java.awt.Color(255, 255, 255));
        lblTC.setText("17");

        lbTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle1.setText("Số Hóa đơn");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Order1.png"))); // NOI18N

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitle1)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lblTC)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(card1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton3.setText("Lọc");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Lọc");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        dcTG1.setDateFormatString("yyyy-MM-dd");

        dcTG2.setDateFormatString("yyyy-MM-dd");

        jButton5.setText("Lọc");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("THỐNG KÊ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addGap(71, 71, 71)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(24, 24, 24)
                                        .addComponent(jButton3))
                                    .addComponent(ycNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4))
                                    .addComponent(mcThang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dcTG1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dcTG2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(barchart, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(card2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(card6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(card1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addComponent(jLabel17))
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jLabel6))
                    .addComponent(ycNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcTG1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcTG2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mcThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20)
                .addComponent(barchart, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dcTG1, dcTG2});

    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        byYear();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        byMonth();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        byTime();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barchart;
    private poly.edu.Panels.Card card1;
    private poly.edu.Panels.Card card2;
    private poly.edu.Panels.Card card3;
    private poly.edu.Panels.Card card6;
    private com.toedter.calendar.JDateChooser dcTG1;
    private com.toedter.calendar.JDateChooser dcTG2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel lbTitle3;
    private javax.swing.JLabel lbTitle6;
    private javax.swing.JLabel lblDT;
    private javax.swing.JLabel lblDT_NT;
    private javax.swing.JLabel lblHuy;
    private javax.swing.JLabel lblKH;
    private javax.swing.JLabel lblTC;
    private com.toedter.calendar.JMonthChooser mcThang;
    private com.toedter.calendar.JYearChooser ycNam;
    // End of variables declaration//GEN-END:variables

}
