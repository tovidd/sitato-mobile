<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class pengadaanSparepart extends Model
{
    protected $table= 'pengadaan_sparepart';
    protected $primaryKey= 'id_pengadaan_sparepart';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'id_cabang',
        'id_supplier',
        'tanggal_pengadaan_sparepart',
    ];
}
