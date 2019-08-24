<?php

use Illuminate\Database\Seeder;
use sitato\transaksiPenjualanJasaService;

class transaksiPenjualanJasaServiceSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        transaksiPenjualanJasaService::create([
            'id_transaksi'=> 1,
            'id_montir'=> 4,
            'id_jasa_service'=> 1,
            'id_kendaraan'=> 1,
            'jumlah_transaksi_penjualan_jasa_service'=> 1,
            'subtotal_transaksi_penjualan_jasa_service'=> 200000
        ]);
        transaksiPenjualanJasaService::create([
            'id_transaksi'=> 1,
            'id_montir'=> 4,
            'id_jasa_service'=> 2,
            'id_kendaraan'=> 1,
            'jumlah_transaksi_penjualan_jasa_service'=> 1,
            'subtotal_transaksi_penjualan_jasa_service'=> 30000
        ]);
        transaksiPenjualanJasaService::create([
            'id_transaksi'=> 2,
            'id_montir'=> 4,
            'id_jasa_service'=> 1,
            'id_kendaraan'=> 2,
            'jumlah_transaksi_penjualan_jasa_service'=> 1,
            'subtotal_transaksi_penjualan_jasa_service'=> 200000
        ]);
        transaksiPenjualanJasaService::create([
            'id_transaksi'=> 2,
            'id_montir'=> 4,
            'id_jasa_service'=> 2,
            'id_kendaraan'=> 2,
            'jumlah_transaksi_penjualan_jasa_service'=> 1,
            'subtotal_transaksi_penjualan_jasa_service'=> 30000
        ]);
        transaksiPenjualanJasaService::create([
            'id_transaksi'=> 3,
            'id_montir'=> 4,
            'id_jasa_service'=> 1,
            'id_kendaraan'=> 3,
            'jumlah_transaksi_penjualan_jasa_service'=> 1,
            'subtotal_transaksi_penjualan_jasa_service'=> 200000
        ]);
        transaksiPenjualanJasaService::create([
            'id_transaksi'=> 3,
            'id_montir'=> 4,
            'id_jasa_service'=> 2,
            'id_kendaraan'=> 3,
            'jumlah_transaksi_penjualan_jasa_service'=> 1,
            'subtotal_transaksi_penjualan_jasa_service'=> 30000
        ]);
    }
}
