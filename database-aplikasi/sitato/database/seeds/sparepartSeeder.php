<?php

use Illuminate\Database\Seeder;
use sitato\sparepart;

class sparepartSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        sparepart::create([
            'kode_sparepart'=> '0001-MRT-001',
            'nama_sparepart'=> 'Lampu motor',
            'jenis_sparepart'=> 'Lampu',
            'stok_sparepart'=> 43,
            'stok_minimum_sparepart'=> 20,
            'rak_sparepart'=> 'DPN-KACA-01',
            'harga_beli_sparepart'=> 5000,
            'harga_jual_sparepart'=> 10000,
            'merek_kendaraan_sparepart'=> 'Honda',
            'jenis_kendaraan_sparepart'=> 'Supra fit',
            'gambar_sparepart'=> ''
        ]);
        sparepart::create([
            'kode_sparepart'=> '0002-MRT-002',
            'nama_sparepart'=> 'Busi motor',
            'jenis_sparepart'=> 'Busi',
            'stok_sparepart'=> 51,
            'stok_minimum_sparepart'=> 20,
            'rak_sparepart'=> 'DPN-KACA-02',
            'harga_beli_sparepart'=> 8000,
            'harga_jual_sparepart'=> 16000,
            'merek_kendaraan_sparepart'=> 'Honda',
            'jenis_kendaraan_sparepart'=> 'Supra X',
            'gambar_sparepart'=> ''
        ]);
        sparepart::create([
            'kode_sparepart'=> '0001-PAM-001',
            'nama_sparepart'=> 'Ban motor',
            'jenis_sparepart'=> 'Ban',
            'stok_sparepart'=> 29,
            'stok_minimum_sparepart'=> 5,
            'rak_sparepart'=> 'BLK-KAYU-01',
            'harga_beli_sparepart'=> 150000,
            'harga_jual_sparepart'=> 200000,
            'merek_kendaraan_sparepart'=> 'Honda',
            'jenis_kendaraan_sparepart'=> 'Supra fit',
            'gambar_sparepart'=> ''
        ]);
        sparepart::create([
            'kode_sparepart'=> '0002-PAM-002',
            'nama_sparepart'=> 'Velg motor',
            'jenis_sparepart'=> 'Velg',
            'stok_sparepart'=> 26,
            'stok_minimum_sparepart'=> 5,
            'rak_sparepart'=> 'BLK-KAYU-02',
            'harga_beli_sparepart'=> 250000,
            'harga_jual_sparepart'=> 350000,
            'merek_kendaraan_sparepart'=> 'Honda',
            'jenis_kendaraan_sparepart'=> 'Supra X',
            'gambar_sparepart'=> ''
        ]);
        sparepart::create([
            'kode_sparepart'=> '0001-JOA-001',
            'nama_sparepart'=> 'Kampas depan motor',
            'jenis_sparepart'=> 'Kampas',
            'stok_sparepart'=> 52,
            'stok_minimum_sparepart'=> 20,
            'rak_sparepart'=> 'TGH-KACA-01',
            'harga_beli_sparepart'=> 10000,
            'harga_jual_sparepart'=> 20000,
            'merek_kendaraan_sparepart'=> 'Honda',
            'jenis_kendaraan_sparepart'=> 'Supra fit',
            'gambar_sparepart'=> ''
        ]);
        sparepart::create([
            'kode_sparepart'=> '0002-JOA-002',
            'nama_sparepart'=> 'Kampas belakang motor',
            'jenis_sparepart'=> 'Kampas',
            'stok_sparepart'=> 47,
            'stok_minimum_sparepart'=> 20,
            'rak_sparepart'=> 'TGH-KACA-02',
            'harga_beli_sparepart'=> 15000,
            'harga_jual_sparepart'=> 30000,
            'merek_kendaraan_sparepart'=> 'Honda',
            'jenis_kendaraan_sparepart'=> 'Supra X',
            'gambar_sparepart'=> ''
        ]);
    }
}
