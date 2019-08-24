<?php

use Illuminate\Database\Seeder;
use sitato\transaksiPenjualanSparepart;

class transaksiPenjualanSparepartSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        transaksiPenjualanSparepart::create([
            'id_transaksi'=> 1,
            'id_sparepart'=> 1,
            'jumlah_transaksi_penjualan_sparepart'=> 1,
            'subtotal_transaksi_penjualan_sparepart'=> 10000
        ]); 
        transaksiPenjualanSparepart::create([
            'id_transaksi'=> 1,
            'id_sparepart'=> 2,
            'jumlah_transaksi_penjualan_sparepart'=> 1,
            'subtotal_transaksi_penjualan_sparepart'=> 16000
        ]);
        transaksiPenjualanSparepart::create([
            'id_transaksi'=> 2,
            'id_sparepart'=> 1,
            'jumlah_transaksi_penjualan_sparepart'=> 1,
            'subtotal_transaksi_penjualan_sparepart'=> 10000
        ]);
        transaksiPenjualanSparepart::create([
            'id_transaksi'=> 2,
            'id_sparepart'=> 2,
            'jumlah_transaksi_penjualan_sparepart'=> 1,
            'subtotal_transaksi_penjualan_sparepart'=> 16000
        ]);
        transaksiPenjualanSparepart::create([
            'id_transaksi'=> 3,
            'id_sparepart'=> 1,
            'jumlah_transaksi_penjualan_sparepart'=> 1,
            'subtotal_transaksi_penjualan_sparepart'=> 10000
        ]);
        transaksiPenjualanSparepart::create([
            'id_transaksi'=> 3,
            'id_sparepart'=> 2,
            'jumlah_transaksi_penjualan_sparepart'=> 1,
            'subtotal_transaksi_penjualan_sparepart'=> 16000
        ]);
        
    }
}
