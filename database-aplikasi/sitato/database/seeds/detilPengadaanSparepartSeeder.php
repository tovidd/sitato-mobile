<?php

use Illuminate\Database\Seeder;
use sitato\detilPengadaanSparepart;

class detilPengadaanSparepartSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        detilPengadaanSparepart::create([
            'id_pengadaan_sparepart'=> 1,
            'id_sparepart'=> 1,
            'satuan_detil_pengadaan_sparepart'=> 'Buah',
            'jumlah_beli_detil_pengadaan_sparepart'=> 5,
            'subtotal_detil_pengadaan_sparepart'=> 150000,
        ]);
        detilPengadaanSparepart::create([
            'id_pengadaan_sparepart'=> 1,
            'id_sparepart'=> 2,
            'satuan_detil_pengadaan_sparepart'=> 'Buah',
            'jumlah_beli_detil_pengadaan_sparepart'=> 10,
            'subtotal_detil_pengadaan_sparepart'=> 200000,
        ]);
        detilPengadaanSparepart::create([
            'id_pengadaan_sparepart'=> 2,
            'id_sparepart'=> 1,
            'satuan_detil_pengadaan_sparepart'=> 'Buah',
            'jumlah_beli_detil_pengadaan_sparepart'=> 30,
            'subtotal_detil_pengadaan_sparepart'=> 300000,
        ]);
        detilPengadaanSparepart::create([
            'id_pengadaan_sparepart'=> 2,
            'id_sparepart'=> 2,
            'satuan_detil_pengadaan_sparepart'=> 'Buah',
            'jumlah_beli_detil_pengadaan_sparepart'=> 50,
            'subtotal_detil_pengadaan_sparepart'=> 250000,
        ]);
    }
}

