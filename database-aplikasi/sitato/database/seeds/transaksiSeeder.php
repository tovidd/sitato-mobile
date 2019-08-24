<?php

use Illuminate\Database\Seeder;
use sitato\transaksi;

class transaksiSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        transaksi::create([
            'id_pelanggan'=> 1,
            'id_cs'=> 2,
            'id_kasir'=> 3,
            'id_status_pengerjaan'=> 2,
            'total_transaksi'=> 200000+30000+10000+16000,
            'diskon_transaksi'=> 20000,
            'jumlah_uang_pembayaran_transaksi'=> 200000+30000+10000+16000-20000
        ]); 
        transaksi::create([
            'id_pelanggan'=> 2,
            'id_cs'=> 2,
            'id_kasir'=> 3,
            'id_status_pengerjaan'=> 2,
            'total_transaksi'=> 200000+30000+10000+16000,
            'diskon_transaksi'=> 20000,
            'jumlah_uang_pembayaran_transaksi'=> 200000+30000+10000+16000-20000
        ]);
        transaksi::create([
            'id_pelanggan'=> 3,
            'id_cs'=> 2,
            'id_kasir'=> 3,
            'id_status_pengerjaan'=> 2,
            'total_transaksi'=> 200000+30000+10000+16000,
            'diskon_transaksi'=> 20000,
            'jumlah_uang_pembayaran_transaksi'=> 200000+30000+10000+16000-20000
        ]);
    }
}
