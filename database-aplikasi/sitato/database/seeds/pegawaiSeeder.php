<?php

use Illuminate\Database\Seeder; 
use sitato\pegawai;

class pegawaiSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        pegawai::create([
            'id_role'=> 1,
            'id_cabang'=> 1,
            'no_telepon_pegawai'=> '082222233333',
            'nama_pegawai'=> 'Philip',
            'alamat_pegawai'=> 'Jalan babarsari nomor 1 blok 1z',
            'gaji_pegawai'=> 2000000,
            'username_pegawai'=> 'Philip',
            'sandi_pegawai'=> 'sukasuka'
        ]);
        pegawai::create([
            'id_role'=> 2,
            'id_cabang'=> 1,
            'no_telepon_pegawai'=> '081111122222',
            'nama_pegawai'=> 'Natalia',
            'alamat_pegawai'=> 'Jalan babarsari nomor 1 blok 2z',
            'gaji_pegawai'=> 1000000,
            'username_pegawai'=> 'Natalia',
            'sandi_pegawai'=> 'sukasuka'
        ]);
        pegawai::create([
            'id_role'=> 3,
            'id_cabang'=> 1,
            'no_telepon_pegawai'=> '083333344444',
            'nama_pegawai'=> 'Rany',
            'alamat_pegawai'=> 'Jalan babarsari nomor 1 blok 3z',
            'gaji_pegawai'=> 1000000,
            'username_pegawai'=> 'Rany',
            'sandi_pegawai'=> 'sukasuka'
        ]);
        pegawai::create([
            'id_role'=> 4,
            'id_cabang'=> 1,
            'no_telepon_pegawai'=> '084444455555',
            'nama_pegawai'=> 'Rendy',
            'alamat_pegawai'=> 'Jalan babarsari nomor 1 blok 4z',
            'gaji_pegawai'=> 1000000,
            'username_pegawai'=> 'Rendy',
            'sandi_pegawai'=> 'sukasuka'
        ]);
    }
}
