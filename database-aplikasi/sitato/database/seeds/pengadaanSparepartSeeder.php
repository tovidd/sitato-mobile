<?php

use Illuminate\Database\Seeder;
use sitato\pengadaanSparepart;
use Carbon\carbon;

class pengadaanSparepartSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        pengadaanSparepart::create([
            'id_cabang'=> 1,
            'id_supplier'=> 1,
            'tanggal_pengadaan_sparepart'=> Carbon::parse('2019-02-28'),
        ]);
        pengadaanSparepart::create([
            'id_cabang'=> 1,
            'id_supplier'=> 2,
            'tanggal_pengadaan_sparepart'=> Carbon::parse('2019-02-28'),
        ]);
        pengadaanSparepart::create([
            'id_cabang'=> 2,
            'id_supplier'=> 1,
            'tanggal_pengadaan_sparepart'=> Carbon::parse('2019-02-28'),
        ]);
        pengadaanSparepart::create([
            'id_cabang'=> 2,
            'id_supplier'=> 2,
            'tanggal_pengadaan_sparepart'=> Carbon::parse('2019-02-28'),
        ]);
    }
}
