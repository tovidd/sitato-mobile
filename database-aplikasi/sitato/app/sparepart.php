<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class sparepart extends Model
{
    protected $table= 'sparepart';
    protected $primaryKey= 'id_sparepart';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'kode_sparepart',
        'nama_sparepart',
        'jenis_sparepart',
        'stok_sparepart',
        'stok_minimum_sparepart',
        'rak_sparepart',
        'harga_beli_sparepart',
        'harga_jual_sparepart',
        'merek_kendaraan_sparepart',
        'jenis_kendaraan_sparepart',
        'gambar_sparepart'
    ];

}
